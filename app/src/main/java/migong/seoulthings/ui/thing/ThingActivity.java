package migong.seoulthings.ui.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public class ThingActivity extends AppCompatActivity implements ThingView {

  private static final String TAG = ThingActivity.class.getSimpleName();

  private String mThingId;
  private ThingPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.thing_activity);

    final Intent intent = getIntent();
    final Bundle args = intent.getExtras();
    if (args == null) {
      Log.e(TAG, "onCreate: args is NULL.");
      finish();
      return;
    }

    mThingId = args.getString(KEY_THING_ID);
    if (StringUtils.isEmpty(mThingId)) {
      Log.e(TAG, "onCreate: thingId is empty. thingId is " + mThingId);
      finish();
      return;
    }

    mPresenter = new ThingPresenter(this, mThingId);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }
}
