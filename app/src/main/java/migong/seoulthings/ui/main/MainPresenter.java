package migong.seoulthings.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import migong.seoulthings.ui.Presenter;

public class MainPresenter implements Presenter {

  private static final String TAG = MainPresenter.class.getSimpleName();

  @NonNull
  private final MainView mView;

  public MainPresenter(@NonNull MainView view) {
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

  public boolean onBottomNavigationItemSelected(@NonNull MenuItem item) {
    Log.d(TAG, "onBottomNavigationItemSelected() called with: item = [" + item + "]");

    // TODO(@gihwan): replace fragment

    return true;
  }
}
