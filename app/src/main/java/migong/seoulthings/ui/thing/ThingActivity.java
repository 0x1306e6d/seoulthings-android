package migong.seoulthings.ui.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public class ThingActivity extends AppCompatActivity implements ThingView {

  private static final String TAG = ThingActivity.class.getSimpleName();

  private TextView mTitleText;
  private ContentLoadingProgressBar mProgressBar;
  private ConstraintLayout mDetailLayout;
  private SupportMapFragment mGoogleMapFragment;

  private String mThingId;
  private GoogleMap mGoogleMap;
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
    setupDetailLayout();
    setupGoogleMap();

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
  public void setGoogleMap(String title, double latitude, double longitude) {
    if (mGoogleMap == null) {
      return;
    }

    LatLng location = new LatLng(latitude, longitude);
    mGoogleMap.addMarker(new MarkerOptions().position(location).title(title));
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
  }

  @Override
  public void finishLoading() {
    if (mProgressBar != null) {
      mProgressBar.hide();
    }
    if (mDetailLayout != null) {
      mDetailLayout.setVisibility(View.VISIBLE);
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

  private void setupDetailLayout() {
    mDetailLayout = findViewById(R.id.thing_detail_layout);
    mDetailLayout.setVisibility(View.GONE);
  }

  private void setupGoogleMap() {
    mGoogleMapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.thing_map);
    if (mGoogleMapFragment == null) {
      return;
    }

    mGoogleMapFragment.getMapAsync(googleMap -> mGoogleMap = googleMap);
  }
}
