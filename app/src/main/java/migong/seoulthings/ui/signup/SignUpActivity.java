package migong.seoulthings.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import migong.seoulthings.R;
import migong.seoulthings.ui.signin.SignInActivity;
import org.apache.commons.lang3.StringUtils;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

  private TextInputEditText mEmailEditText;
  private TextInputEditText mPasswordEditText;
  private Button mSignUpButton;
  private Button mSignInButton;

  private SignUpPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signup_activity);

    mEmailEditText = findViewById(R.id.signup_email_edittext);
    mPasswordEditText = findViewById(R.id.signup_password_edittext);
    mSignUpButton = findViewById(R.id.signup_button);
    mSignUpButton.setOnClickListener(v -> mPresenter.onSignUpButtonClicked());
    mSignInButton = findViewById(R.id.signup_signin_button);
    mSignInButton.setOnClickListener(v -> mPresenter.onSignInButtonClicked());

    mPresenter = new SignUpPresenter(this);
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
  public String getEmail() {
    return mEmailEditText == null ? StringUtils.EMPTY : mEmailEditText.getText().toString();
  }

  @Override
  public String getPassword() {
    return mPasswordEditText == null ? StringUtils.EMPTY : mPasswordEditText.getText().toString();
  }

  @Override
  public void startSignInActivity() {
    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }

  @Override
  public void showSignUpFailure() {
    Snackbar.make(mSignUpButton, R.string.failed_to_signup, Snackbar.LENGTH_SHORT)
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
