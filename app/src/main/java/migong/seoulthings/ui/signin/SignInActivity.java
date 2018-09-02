package migong.seoulthings.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import migong.seoulthings.R;
import migong.seoulthings.ui.main.MainActivity;
import migong.seoulthings.ui.signup.SignUpActivity;
import org.apache.commons.lang3.StringUtils;

public class SignInActivity extends AppCompatActivity implements SignInView {

  private TextInputEditText mEmailEditText;
  private TextInputEditText mPasswordEditText;
  private Button mSignInButton;
  private Button mSignUpButton;
  private SignInButton mGoogleSignInButton;
  private LoginButton mFacebookSignInButton;

  private GoogleSignInClient mGoogleSignInClient;
  private CallbackManager mCallbackManager;
  private SignInPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signin_activity);

    mEmailEditText = findViewById(R.id.signin_email_edittext);
    mPasswordEditText = findViewById(R.id.signin_password_edittext);
    mSignInButton = findViewById(R.id.signin_button);
    mSignInButton.setOnClickListener(v -> mPresenter.onSignInButtonClicked());
    mSignUpButton = findViewById(R.id.signin_signup_button);
    mSignUpButton.setOnClickListener(v -> mPresenter.onSignUpButtonClicked());
    mGoogleSignInButton = findViewById(R.id.signin_google_button);
    mGoogleSignInButton.setSize(SignInButton.SIZE_ICON_ONLY);
    mGoogleSignInButton.setOnClickListener(v -> mPresenter.onGoogleSignInButtonClicked());

    mCallbackManager = CallbackManager.Factory.create();
    mFacebookSignInButton = findViewById(R.id.signin_facebook_button);
    mFacebookSignInButton.setReadPermissions("email", "public_profile");
    mFacebookSignInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        mPresenter.onFacebookSignInSuccess(loginResult);
      }

      @Override
      public void onCancel() {
        mPresenter.onFacebookSignInCancel();
      }

      @Override
      public void onError(FacebookException error) {
        mPresenter.onFacebookSignInError(error);
      }
    });

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    mPresenter = new SignInPresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case RC_GOOGLE_SIGN_IN:
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        mPresenter.completeGoogleSignIn(task);
        break;
      default:
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        break;
    }
  }

  @Override
  public String getEmail() {
    return mEmailEditText == null ? StringUtils.EMPTY : mEmailEditText.getText().toString();
  }

  @Override
  public String getPassword() {
    return mPasswordEditText == null ? StringUtils.EMPTY : mPasswordEditText.getText().toString();
  }

  @Override
  public void startMainActivity() {
    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  public void startSignUpActivity() {
    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
    startActivity(intent);
  }

  @Override
  public void startGoogleSignInIntent() {
    Intent intent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
  }

  @Override
  public void showSignInFailure() {
    Snackbar.make(mSignInButton, R.string.failed_to_signin, Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override
  public void showValidEmailInputRequest() {
    mEmailEditText.requestFocus();
    Snackbar.make(mEmailEditText, R.string.valid_email_input_request, Snackbar.LENGTH_SHORT)
        .show();
  }

  @Override
  public void showValidPasswordInputRequest() {
    mPasswordEditText.requestFocus();
    Snackbar.make(mPasswordEditText, R.string.valid_password_input_request, Snackbar.LENGTH_SHORT)
        .show();
  }
}
