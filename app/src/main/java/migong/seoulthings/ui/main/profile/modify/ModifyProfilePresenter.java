package migong.seoulthings.ui.main.profile.modify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import io.reactivex.disposables.CompositeDisposable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import migong.seoulthings.SeoulThingsConstants;
import migong.seoulthings.ui.Presenter;

public class ModifyProfilePresenter implements Presenter {

  private static final String TAG = ModifyProfilePresenter.class.getSimpleName();

  private boolean mPhotoChanged;
  private FirebaseAuth mAuth;
  private FirebaseUser mUser;
  private FirebaseStorage mStorage;
  private Uri mCurrentPhotoUri;
  @NonNull
  private final CompositeDisposable mCompositeDisposable;
  @NonNull
  private final ModifyProfileView mView;

  public ModifyProfilePresenter(@NonNull ModifyProfileView view) {
    mView = view;
    mCompositeDisposable = new CompositeDisposable();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    mAuth = FirebaseAuth.getInstance();
    mUser = mAuth.getCurrentUser();
    if (mUser == null) {
      Log.e(TAG, "onCreate: user is NULL.");
      return;
    }

    mStorage = FirebaseStorage.getInstance();
  }

  @Override
  public void onResume() {
    Log.d(TAG, "onResume() called");

    mView.bindProfile(mUser);
  }

  @Override
  public void onPause() {
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy() called");
  }

  public void onRequestTakePhotoSuccess(Intent data) {
    Uri photoUri = data.getData();
    if (photoUri == null) {
      Log.e(TAG, "onRequestTakePhotoSuccess: photoUri is NULL.");
      return;
    }

    mPhotoChanged = true;
    mCurrentPhotoUri = photoUri;
    mView.changePhoto(photoUri);
  }

  public void onCompleteButtonClicked() {
    if (mUser == null) {
      return;
    }
    mView.startUpdateProfile();

    if (mPhotoChanged) {
      Log.d(TAG, "onCompleteButtonClicked: photo is changed.");

      try {
        uploadPhoto()
            .continueWithTask(task -> {
              if (!task.isSuccessful()) {
                if (task.getException() == null) {
                  throw new Exception("Failed to get download url from Firebase Storage.");
                } else {
                  throw task.getException();
                }
              }

              final Uri photoUri = task.getResult();
              final UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                  .setPhotoUri(photoUri)
                  .setDisplayName(mView.getDisplayName())
                  .build();
              return mUser.updateProfile(request);
            })
            .addOnCompleteListener(task -> mView.finishUpdateProfile())
            .addOnFailureListener(error -> {
              Log.e(TAG, "Failed to update profile.", error);
            })
            .addOnSuccessListener(v -> mView.finish());
      } catch (IOException e) {
        Log.e(TAG, "Failed to upload photo.", e);
      }
    } else {
      Log.d(TAG, "onCompleteButtonClicked: photo is not changed.");

      final UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
          .setDisplayName(mView.getDisplayName())
          .build();
      mUser.updateProfile(request)
          .addOnCompleteListener(task -> mView.finishUpdateProfile())
          .addOnFailureListener(error -> {
            Log.e(TAG, "Failed to update profile.", error);
          })
          .addOnSuccessListener(v -> mView.finish());
    }
  }

  public void onChangePhotoButtonClicked() {
    mView.startTakePhotoIntent();
  }

  public void onSignOutButtonClicked() {
    mAuth.signOut();
    mView.startSignInActivity();
  }

  private byte[] resizePhoto() throws IOException {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      final Bitmap bitmap = Picasso.get()
          .load(mCurrentPhotoUri)
          .resize(SeoulThingsConstants.PROFILE_PHOTO_SIZE, SeoulThingsConstants.PROFILE_PHOTO_SIZE)
          .get();
      bitmap.compress(CompressFormat.JPEG, 100, outputStream);
      return outputStream.toByteArray();
    }
  }

  private Task<Uri> uploadPhoto() throws IOException {
    final byte[] data = resizePhoto();

    final StorageReference reference = mStorage.getReference();
    final StorageReference photosRef = reference.child("photos");
    final StorageReference photoRef = photosRef.child(mUser.getUid());

    return photoRef.putBytes(data)
        .continueWithTask(task -> {
          if (!task.isSuccessful()) {
            if (task.getException() == null) {
              throw new Exception("Failed to upload photo to Firebase Storage.");
            } else {
              throw task.getException();
            }
          }

          return photoRef.getDownloadUrl();
        });
  }
}
