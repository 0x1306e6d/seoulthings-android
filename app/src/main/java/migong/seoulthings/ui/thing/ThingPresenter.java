package migong.seoulthings.ui.thing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;

public class ThingPresenter implements Presenter {

  private static final String TAG = ThingPresenter.class.getSimpleName();

  @NonNull
  private final String mThingId;
  @NonNull
  private final ThingView mView;

  public ThingPresenter(@NonNull ThingView view, @NonNull String thingId) {
    mView = view;
    mThingId = thingId;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    Log.d(TAG, "onCreate: thingId is " + mThingId);
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
