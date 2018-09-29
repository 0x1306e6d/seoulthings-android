package migong.seoulthings.ui.thing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import io.reactivex.disposables.CompositeDisposable;
import migong.seoulthings.R;
import migong.seoulthings.data.Review;
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
  private CompositeDisposable mCompositeDisposable;
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

    mCompositeDisposable = new CompositeDisposable();

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

    mCompositeDisposable.dispose();
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
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13.0f));
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
  public void showReviewDialog(@Nullable Review review) {
    final LayoutInflater inflater = getLayoutInflater();
    final View view = inflater.inflate(R.layout.review_dialog, null);
    final AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setView(view);

    final RatingBar ratingBar = view.findViewById(R.id.review_dialog_rating_bar);
    final EditText contentsText = view.findViewById(R.id.review_dialog_contents);
    final Button dismissButton = view.findViewById(R.id.review_dialog_dismiss_button);
    final Button submitButton = view.findViewById(R.id.review_dialog_submit_button);
    final AlertDialog dialog = builder.create();
    dismissButton.setOnClickListener(v -> dialog.dismiss());
    if (review == null) {
      submitButton.setOnClickListener(
          v -> mCompositeDisposable.add(
              mPresenter.createReview(contentsText.getText().toString(), ratingBar.getRating())
                  .subscribe(dialog::dismiss, error -> Log.e(TAG, "createReview: error", error))
          )
      );
    } else {
      ratingBar.setRating(review.getRating());
      contentsText.setText(review.getContents());
      submitButton.setOnClickListener(
          v -> mCompositeDisposable.add(
              mPresenter
                  .modifyReview(review, contentsText.getText().toString(), ratingBar.getRating())
                  .subscribe(dialog::dismiss, error -> Log.e(TAG, "modifyReview: error", error))
          )
      );
    }
    dialog.show();
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
    if (mGoogleMapFragment != null) {
      mGoogleMapFragment.getMapAsync(googleMap -> mGoogleMap = googleMap);
    }

    mAddressText = findViewById(R.id.thing_address);
    mContentsText = findViewById(R.id.thing_contents);
  }

  private void setupReviewRecycler() {
    mReviewRecyclerView = findViewById(R.id.thing_reviews_recycler);
    mReviewRecyclerView.setHasFixedSize(true);
    mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mReviewRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    mReviewRecyclerAdapter = new ReviewRecyclerAdapter(mCompositeDisposable,
        () -> mPresenter.onMakeReviewSuggestionClicked(),
        review -> mPresenter.onModifyReviewButtonClicked(review));
    mReviewRecyclerView.setAdapter(mReviewRecyclerAdapter);
  }
}
