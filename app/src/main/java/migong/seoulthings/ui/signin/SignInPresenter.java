package migong.seoulthings.ui.signin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import migong.seoulthings.ui.Presenter;
import migong.seoulthings.util.AuthenticationUtils;

public class SignInPresenter implements Presenter {

  private static final String TAG = SignInPresenter.class.getSimpleName();

  private FirebaseAuth mAuth;

  @NonNull
  private final SignInView mView;

  public SignInPresenter(@NonNull SignInView view) {
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

  public void onSignInButtonClicked() {
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

    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this::completeSignIn);
  }

  public void onSignUpButtonClicked() {
    // TODO(@gihwan): SignUpActivity 실행
  }

  private void completeSignIn(@NonNull Task<AuthResult> task) {
    if (task.isSuccessful()) {
      // TODO(@gihwan): 로그인 성공 처리
      Log.d(TAG, "completeSignIn: success, user=" + mAuth.getCurrentUser());
    } else {
      mView.showSignInFailure();
      Log.w(TAG, "completeSignIn: failure.", task.getException());
    }
  }
}
