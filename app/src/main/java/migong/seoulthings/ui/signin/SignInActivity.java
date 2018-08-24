package migong.seoulthings.ui.signin;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public class SignInActivity extends AppCompatActivity implements SignInView {

  private TextInputEditText mEmailEditText;
  private TextInputEditText mPasswordEditText;
  private Button mSignInButton;
  private Button mSignUpButton;

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
  public String getEmail() {
    return mEmailEditText == null ? StringUtils.EMPTY : mEmailEditText.getText().toString();
  }

  @Override
  public String getPassword() {
    return mPasswordEditText == null ? StringUtils.EMPTY : mPasswordEditText.getText().toString();
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
