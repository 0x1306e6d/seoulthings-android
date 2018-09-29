package migong.seoulthings.ui.donate;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import migong.seoulthings.R;
import migong.seoulthings.ui.donate.adapter.DonateImagePagerAdapter;

public class DonateActivity extends AppCompatActivity implements DonateView {

  private static final int REQUEST_PICK_PHOTO = 0x00000001;

  private static final int PERMISSION_FOR_COARSE_LOCATION = 0x00000001;
  private static final int PERMISSION_FOR_FINE_LOCATION = 0x00000010;

  private static final String TAG = DonateActivity.class.getSimpleName();

  private Button mSubmitButton;
  private ViewPager mImagePager;
  private DonateImagePagerAdapter mImagePagerAdapter;
  private ContentLoadingProgressBar mAddressLoadingProgressBar;
  private Button mAddressButton;
  private FrameLayout mGoogleMapContainer;
  private SupportMapFragment mGoogleMapFragment;
  private TextInputEditText mTitleEditText;
  private TextInputEditText mContentsEditText;

  private GoogleMap mGoogleMap;
  private Marker mGoogleMapMarker;
  private FusedLocationProviderClient mLocationClient;
  private DonatePresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.donate_activity);

    setupAppBar();
    setupImagePager();
    setupForm();

    mPresenter = new DonatePresenter(this);
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
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    switch (requestCode) {
      case REQUEST_PICK_PHOTO:
        if (resultCode == RESULT_OK) {
          mPresenter.onRequestPickPhotoSuccess(data);
        }
        break;
      default:
        super.onActivityResult(requestCode, resultCode, data);
        break;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_FOR_COARSE_LOCATION:
      case PERMISSION_FOR_FINE_LOCATION:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          setupGoogleMap();
        }
        break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void startPickPhotoIntent() {
    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
    if (pickPhotoIntent.resolveActivity(getPackageManager()) != null) {
      startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);
    }
  }

  @Override
  public void addImage(@NonNull Uri imageUri) {
    mImagePagerAdapter.addImage(imageUri);
  }

  @Override
  public void startAddressLoading() {
    mAddressLoadingProgressBar.show();
  }

  @Override
  public void finishAddressLoading() {
    mAddressLoadingProgressBar.hide();
  }

  @Override
  public void changeAddressButtonText(String address) {
    mAddressButton.setText(address);
  }

  @Override
  public void showGoogleMap() {
    mGoogleMapContainer.setVisibility(View.VISIBLE);
    mAddressButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,
        R.drawable.ic_arrow_up_black_24, 0);
  }

  @Override
  public void hideGoogleMap() {
    mGoogleMapContainer.setVisibility(View.GONE);
    mAddressButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,
        R.drawable.ic_arrow_down_black_24, 0);
  }

  @Override
  public void setMarkerOnGoogleMap(@NonNull LatLng latLng) {
    if (mGoogleMap == null) {
      return;
    }

    if (mGoogleMapMarker != null) {
      mGoogleMapMarker.remove();
    }
    mGoogleMapMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng));
  }

  private void setupAppBar() {
    ImageButton backButton = findViewById(R.id.donate_back_button);
    backButton.setOnClickListener(v -> onBackPressed());

    mSubmitButton = findViewById(R.id.donate_submit_button);
  }

  private void setupImagePager() {
    mImagePager = findViewById(R.id.donate_image_pager);
    mImagePagerAdapter = new DonateImagePagerAdapter(this, mImagePager,
        () -> mPresenter.onAddPhotoButtonClicked());
    mImagePager.setAdapter(mImagePagerAdapter);
  }

  private void setupForm() {
    mAddressLoadingProgressBar = findViewById(R.id.donate_address_loading_progressbar);
    mAddressLoadingProgressBar.hide();
    mAddressButton = findViewById(R.id.donate_address_button);
    mAddressButton.setOnClickListener(v -> mPresenter.onAddressButtonClicked());

    mGoogleMapContainer = findViewById(R.id.donate_map_container);
    mGoogleMapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.donate_map);
    if (mGoogleMapFragment != null) {
      mGoogleMapFragment.getMapAsync(googleMap -> {
        mGoogleMap = googleMap;
        setupGoogleMap();
      });
    }

    mTitleEditText = findViewById(R.id.donate_title_edittext);
    mTitleEditText.clearFocus();
    mContentsEditText = findViewById(R.id.donate_contents_edittext);
    mContentsEditText.clearFocus();
  }

  private void setupGoogleMap() {
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_COARSE_LOCATION},
          PERMISSION_FOR_COARSE_LOCATION);
      return;
    }
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION},
          PERMISSION_FOR_FINE_LOCATION);
      return;
    }

    mGoogleMap.setMyLocationEnabled(true);
    mGoogleMap.setOnMapClickListener(latLng -> mPresenter.onGoogleMapClicked(latLng));
    mLocationClient = LocationServices.getFusedLocationProviderClient(this);
    mLocationClient.getLastLocation()
        .addOnFailureListener(error -> Log.e(TAG, "Failed to get last location.", error))
        .addOnSuccessListener(location -> {
          final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
          mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
        });
  }
}
