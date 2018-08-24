package migong.seoulthings.ui.signin;

public interface SignInView {

  String getEmail();

  String getPassword();

  void startSignUpActivity();

  void showSignInFailure();

  void showValidEmailInputRequest();

  void showValidPasswordInputRequest();

}
