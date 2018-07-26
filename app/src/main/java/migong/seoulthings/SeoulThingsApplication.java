package migong.seoulthings;

import android.app.Application;
import android.util.Log;

public class SeoulThingsApplication extends Application {

  private static final String TAG = SeoulThingsApplication.class.getSimpleName();

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d(TAG, "onCreate() called");
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    Log.d(TAG, "onTerminate() called");
  }
}
