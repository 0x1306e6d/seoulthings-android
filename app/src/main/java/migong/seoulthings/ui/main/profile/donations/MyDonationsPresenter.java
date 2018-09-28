package migong.seoulthings.ui.main.profile.donations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;

public class MyDonationsPresenter implements Presenter {

  private static final String TAG = MyDonationsPresenter.class.getSimpleName();

  @NonNull
  private MyDonationsView mView;

  public MyDonationsPresenter(@NonNull MyDonationsView view) {
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
}
