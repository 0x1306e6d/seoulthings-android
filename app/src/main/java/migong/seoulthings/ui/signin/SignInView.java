package migong.seoulthings.ui.signin;

public interface SignInView {

  int RC_GOOGLE_SIGN_IN = 0;

  String getEmail();

  String getPassword();

  void startSignUpActivity();

  void startGoogleSignInIntent();

  void showSignInFailure();

  void showValidEmailInputRequest();

  void showValidPasswordInputRequest();

}
