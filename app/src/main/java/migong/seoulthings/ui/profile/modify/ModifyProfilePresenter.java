package migong.seoulthings.ui.profile.modify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;

public class ModifyProfilePresenter implements Presenter {

  private static final String TAG = ModifyProfilePresenter.class.getSimpleName();

  @NonNull
  private final ModifyProfileView mView;

  public ModifyProfilePresenter(@NonNull ModifyProfileView view) {
    mView = view;
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

  public void onCompleteButtonClicked() {
    Log.d(TAG, "onCompleteButtonClicked() called");
  }
}
