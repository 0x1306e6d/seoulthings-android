package migong.seoulthings.ui.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public class ThingActivity extends AppCompatActivity implements ThingView {

  private static final String TAG = ThingActivity.class.getSimpleName();

  private TextView mTitleText;
  private ContentLoadingProgressBar mProgressBar;

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

    setupAppBar();
    setupInteraction();

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

  @Override
  public void setTitle(String title) {
    if (mTitleText != null) {
      mTitleText.setText(title);
    }
  }

  @Override
  public void hideProgressBar() {
    if (mProgressBar != null) {
      mProgressBar.hide();
    }
  }

  private void setupAppBar() {
    ImageButton backButton = findViewById(R.id.thing_back_button);
    backButton.setOnClickListener(v -> onBackPressed());

    mTitleText = findViewById(R.id.thing_title);
  }

  private void setupInteraction() {
    mProgressBar = findViewById(R.id.thing_progressbar);
    mProgressBar.show();
  }
}
