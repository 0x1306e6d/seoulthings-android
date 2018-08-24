package migong.seoulthings.ui.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;

public class SignUpPresenter implements Presenter {

  private static final String TAG = SignUpPresenter.class.getSimpleName();

  @NonNull
  private final SignUpView mView;

  public SignUpPresenter(@NonNull SignUpView view) {
    this.mView = view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
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
    // TODO(@gihwan): 회원가입 처리
  }

  public void onSignInButtonClicked() {
    mView.startSignInActivity();
  }
}
