package migong.seoulthings.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import migong.seoulthings.R;
import migong.seoulthings.ui.signin.SignInActivity;

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
  public void startSignInActivity() {
    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }
}
