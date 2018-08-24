package migong.seoulthings.ui.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import migong.seoulthings.ui.Presenter;
import migong.seoulthings.util.AuthenticationUtils;

public class SignUpPresenter implements Presenter {

  private static final String TAG = SignUpPresenter.class.getSimpleName();

  private FirebaseAuth mAuth;

  @NonNull
  private final SignUpView mView;

  public SignUpPresenter(@NonNull SignUpView view) {
    this.mView = view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    mAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void onResume() {
    Log.d(TAG, "onResume() called");
  }

  @Override
  public void onPause() {
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy() called");
  }

  public void onSignUpButtonClicked() {
    final String email = mView.getEmail();
    final String password = mView.getPassword();

    if (!AuthenticationUtils.isValidEmailAddress(email)) {
      mView.showValidEmailInputRequest();
      return;
    }

    if (!AuthenticationUtils.isValidPassword(password)) {
      mView.showValidPasswordInputRequest();
      return;
    }

    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this::completeSignUp);
  }

  public void onSignInButtonClicked() {
    mView.startSignInActivity();
  }

  private void completeSignUp(@NonNull Task<AuthResult> task) {
    if (task.isSuccessful()) {
      final FirebaseUser user = mAuth.getCurrentUser();

      Log.d(TAG, "completeSignUp: success, user=" + user);
    } else {
      mView.showSignUpFailure();
      Log.w(TAG, "completeSignUp: failure", task.getException());
    }
  }
}
