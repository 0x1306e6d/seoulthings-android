package migong.seoulthings.ui.donate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import migong.seoulthings.R;
import migong.seoulthings.SeoulThingsConstants;
import migong.seoulthings.data.Donation;
import migong.seoulthings.ui.Presenter;
import org.apache.commons.lang3.StringUtils;

public class DonatePresenter implements Presenter {

  private static final String TAG = DonatePresenter.class.getSimpleName();

  private boolean mShowGoogleMap;
  private LatLng mLastLatLng;
  private String mLastThoroughfare;
  private FirebaseUser mUser;
  private FirebaseFirestore mFirestore;
  private FirebaseStorage mStorage;
  @NonNull
  private final CompositeDisposable mCompositeDisposable;
  @NonNull
  private final DonateView mView;

  public DonatePresenter(@NonNull DonateView view) {
    mView = view;
    mCompositeDisposable = new CompositeDisposable();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    mUser = FirebaseAuth.getInstance().getCurrentUser();
    mFirestore = FirebaseFirestore.getInstance();
    mStorage = FirebaseStorage.getInstance();
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

  public void onSubmitButtonClicked() {
    if (mUser == null) {
      return;
    }
    if (mLastLatLng == null) {
      mView.showSnackBar(R.string.msg_invalid_location);
      return;
    }
    if (StringUtils.isEmpty(mLastThoroughfare)) {
      mView.showSnackBar(R.string.msg_invalid_thoroughfare);
      return;
    }
    if (StringUtils.isEmpty(mView.getDonationTitle())) {
      mView.showSnackBar(R.string.msg_invalid_title);
      return;
    }
    if (StringUtils.isEmpty(mView.getDonationContents())) {
      mView.showSnackBar(R.string.msg_invalid_contents);
      return;
    }
    if (mView.getDonationImages().size() == 0) {
      mView.showSnackBar(R.string.msg_invalid_images);
      return;
    }
    mView.startSubmit();

    mCompositeDisposable.add(
        createDonation()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                mView::finish,
                error -> {
                  Log.e(TAG, "Failed to create donation.", error);

                  mView.finishSubmit();
                  mView.showSnackBar(R.string.msg_upload_donation_fail);
                }
            )
    );
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

  private Completable createDonation() {
    return Completable.create(emitter -> {
      final Donation donation = new Donation(mUser.getUid(), mView.getDonationTitle(),
          mView.getDonationContents(), mLastThoroughfare, mLastLatLng);
      mFirestore.collection("donations")
          .add(donation)
          .continueWithTask(task -> {
            if (!task.isSuccessful()) {
              if (task.getException() == null) {
                throw new Exception("Failed to add a donation to Firestore.");
              } else {
                throw task.getException();
              }
            }

            final DocumentReference reference = task.getResult();
            final String id = reference.getId();
            return Tasks.whenAll(uploadThumbnail(reference, id), uploadImages(reference, id));
          })
          .addOnSuccessListener(v -> emitter.onComplete())
          .addOnFailureListener(emitter::onError);
    });
  }

  private Task<Void> uploadThumbnail(DocumentReference reference, String id) throws IOException {
    final StorageReference storageRef = mStorage.getReference();
    final StorageReference donationsRef = storageRef.child("donations");
    final StorageReference donationRef = donationsRef.child(id);
    final StorageReference thumbnailRef = donationRef.child("thumbnail");
    return thumbnailRef
        .putBytes(makeThumbnail(mView.getDonationImages().get(0)))
        .continueWithTask(task -> {
          if (!task.isSuccessful()) {
            if (task.getException() == null) {
              throw new Exception("Failed to upload thumbnail to Firebase Storage.");
            } else {
              throw task.getException();
            }
          }

          return thumbnailRef.getDownloadUrl();
        })
        .continueWithTask(task -> {
          if (!task.isSuccessful()) {
            if (task.getException() == null) {
              throw new Exception("Failed to upload get downloadurl from Firebase Storage.");
            } else {
              throw task.getException();
            }
          }

          return reference.update("thumbnailUrl", task.getResult().toString());
        });
  }

  private byte[] makeThumbnail(Uri uri) throws IOException {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      final Bitmap bitmap = Media.getBitmap(mView.getContext().getContentResolver(), uri);
      final Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
          SeoulThingsConstants.THUMBNAIL_SIZE, SeoulThingsConstants.THUMBNAIL_SIZE, true);
      scaledBitmap.compress(CompressFormat.JPEG, 100, outputStream);
      return outputStream.toByteArray();
    }
  }

  private Task<Void> uploadImages(DocumentReference reference, String id) {
    final List<Task<Uri>> tasks = new ArrayList<>();
    final List<Uri> images = mView.getDonationImages();

    for (int i = 0; i < images.size(); i++) {
      tasks.add(uploadImage(id, i, images.get(i)));
    }

    return Tasks.whenAllSuccess(tasks)
        .continueWithTask(task -> {
          final List<String> urls = new ArrayList<>();

          for (Object uri : task.getResult()) {
            urls.add(uri.toString());
          }

          return reference.update("imageUrls", urls);
        });
  }

  private Task<Uri> uploadImage(String id, int index, Uri imageUri) {
    final StorageReference storageRef = mStorage.getReference();
    final StorageReference donationsRef = storageRef.child("donations");
    final StorageReference donationRef = donationsRef.child(id);
    final StorageReference imageRef = donationRef.child(String.valueOf(index));
    return imageRef
        .putFile(imageUri)
        .continueWithTask(task -> {
          if (!task.isSuccessful()) {
            if (task.getException() == null) {
              throw new Exception("Failed to upload image to Firebase Storage.");
            } else {
              throw task.getException();
            }
          }

          return imageRef.getDownloadUrl();
        });
  }
}
