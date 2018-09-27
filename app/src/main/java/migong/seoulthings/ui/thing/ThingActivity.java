package migong.seoulthings.ui.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import migong.seoulthings.R;
import migong.seoulthings.ui.thing.adapter.ReviewRecyclerAdapter;
import org.apache.commons.lang3.StringUtils;

public class ThingActivity extends AppCompatActivity implements ThingView {

  private static final String TAG = ThingActivity.class.getSimpleName();

  private TextView mTitleText;
  private ContentLoadingProgressBar mProgressBar;
  private ScrollView mDetailView;
  private SupportMapFragment mGoogleMapFragment;
  private TextView mAddressText;
  private TextView mContentsText;
  private RecyclerView mReviewRecyclerView;
  private ReviewRecyclerAdapter mReviewRecyclerAdapter;

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
    setupReviewRecycler();

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
  public void setAddress(String address) {
    if (mAddressText != null) {
      mAddressText.setText(address);
    }
  }

  @Override
  public void setContents(String contents) {
    if (mContentsText != null) {
      mContentsText.setText(contents);
    }
  }

  @Override
  public void finishLoading() {
    if (mProgressBar != null) {
      mProgressBar.hide();
    }
    if (mDetailView != null) {
      mDetailView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void addSnapshot(int index, QueryDocumentSnapshot snapshot) {
    if (mReviewRecyclerAdapter != null) {
      mReviewRecyclerAdapter.addSnapshot(index, snapshot);
    }
  }

  @Override
  public void modifySnapshot(int oldIndex, int newIndex, QueryDocumentSnapshot snapshot) {
    if (mReviewRecyclerAdapter != null) {
      mReviewRecyclerAdapter.modifySnapshot(oldIndex, newIndex, snapshot);
    }
  }

  @Override
  public void removeSnapshot(int index) {
    if (mReviewRecyclerAdapter != null) {
      mReviewRecyclerAdapter.removeSnapshot(index);
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
    mDetailView = findViewById(R.id.thing_detail);
    mDetailView.setVisibility(View.GONE);

    mGoogleMapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.thing_map);
    if (mGoogleMapFragment == null) {
      return;
    }
    mGoogleMapFragment.getMapAsync(googleMap -> mGoogleMap = googleMap);

    mAddressText = findViewById(R.id.thing_address);
    mContentsText = findViewById(R.id.thing_contents);
  }

  private void setupReviewRecycler() {
    mReviewRecyclerView = findViewById(R.id.thing_reviews_recycler);
    mReviewRecyclerView.setHasFixedSize(true);
    mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mReviewRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    mReviewRecyclerAdapter = new ReviewRecyclerAdapter(() -> {

    });
    mReviewRecyclerView.setAdapter(mReviewRecyclerAdapter);
  }
}
