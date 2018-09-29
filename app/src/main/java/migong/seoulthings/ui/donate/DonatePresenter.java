package migong.seoulthings.ui.donate;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import migong.seoulthings.ui.Presenter;
import org.apache.commons.lang3.StringUtils;

public class DonatePresenter implements Presenter {

  private static final String TAG = DonatePresenter.class.getSimpleName();

  private boolean mShowGoogleMap;
  private LatLng mLastLatLng;
  private String mLastThoroughfare;
  @NonNull
  private final DonateView mView;

  public DonatePresenter(@NonNull DonateView view) {
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

  public void onAddPhotoButtonClicked() {
    mView.startPickPhotoIntent();
  }

  public void onRequestPickPhotoSuccess(Intent data) {
    Uri photoUri = data.getData();
    if (photoUri == null) {
      Log.e(TAG, "onRequestPickPhotoSuccess: photoUri is NULL.");
      return;
    }

    mView.addImage(photoUri);
  }

  public void onAddressButtonClicked() {
    if (mShowGoogleMap) {
      mView.hideGoogleMap();
      mShowGoogleMap = false;
    } else {
      mView.showGoogleMap();
      mShowGoogleMap = true;
    }
  }

  public void onGoogleMapClicked(@NonNull LatLng latLng) {
    mView.startAddressLoading();

    mLastLatLng = latLng;
    mView.setMarkerOnGoogleMap(latLng);

    try {
      final Geocoder geocoder = new Geocoder(mView.getContext());
      final List<Address> addresses = geocoder
          .getFromLocation(latLng.latitude, latLng.longitude, 1);

      if (addresses != null && addresses.size() > 0) {
        Address address = addresses.get(0);
        mLastThoroughfare = address.getThoroughfare();
      } else {
        mLastThoroughfare = StringUtils.EMPTY;
      }
    } catch (IOException e) {
      Log.e(TAG, "Failed get from location " + latLng, e);
    } finally {
      mView.changeAddressButtonText(mLastThoroughfare);
      mView.finishAddressLoading();
    }
  }
}
