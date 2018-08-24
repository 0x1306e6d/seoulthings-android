package migong.seoulthings.ui.signup;

public interface SignUpView {

  String getEmail();

  String getPassword();

  void startSignInActivity();

  void showSignUpFailure();

  void showValidEmailInputRequest();

  void showValidPasswordInputRequest();

}
