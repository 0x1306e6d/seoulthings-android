package migong.seoulthings.ui.donate;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public interface DonateView {

  Context getContext();

  void finish();

  String getDonationTitle();

  String getDonationContents();

  List<Uri> getDonationImages();

  void startPickPhotoIntent();

  void addImage(@NonNull Uri imageUri);

  void startAddressLoading();

  void finishAddressLoading();

  void changeAddressButtonText(String address);

  void showGoogleMap();

  void hideGoogleMap();

  void setMarkerOnGoogleMap(@NonNull LatLng latLng);

  void showSnackBar(@StringRes int messageResId);

  void startSubmit();

  void finishSubmit();
}
