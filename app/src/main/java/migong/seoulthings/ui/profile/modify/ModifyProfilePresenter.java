package migong.seoulthings.ui.profile.modify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import migong.seoulthings.ui.Presenter;

public class ModifyProfilePresenter implements Presenter {

  private static final String TAG = ModifyProfilePresenter.class.getSimpleName();

  private FirebaseUser mUser;
  private FirebaseStorage mStorage;
  private Uri mCurrentPhotoUri;
  @NonNull
  private final ModifyProfileView mView;

  public ModifyProfilePresenter(@NonNull ModifyProfileView view) {
    mView = view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    mUser = FirebaseAuth.getInstance().getCurrentUser();
    if (mUser == null) {
      Log.e(TAG, "onCreate: user is NULL.");
      return;
    }

    mStorage = FirebaseStorage.getInstance();

    mView.changePhoto(mUser.getPhotoUrl());
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

  public void onRequestTakePhotoSuccess(Intent data) {
    Uri photoUri = data.getData();
    if (photoUri == null) {
      Log.e(TAG, "onRequestTakePhotoSuccess: photoUri is NULL.");
      return;
    }

    mCurrentPhotoUri = photoUri;
    mView.changePhoto(photoUri);
  }

  public void onCompleteButtonClicked() {
    if (mUser == null) {
      return;
    }
    mView.startUpdateProfile();

    final StorageReference reference = mStorage.getReference();
    final StorageReference photosRef = reference.child("photos");
    final StorageReference photoRef = photosRef.child(mUser.getUid());
    photoRef.putFile(mCurrentPhotoUri)
        .continueWithTask(task -> {
          if (!task.isSuccessful()) {
            if (task.getException() == null) {
              throw new Exception("Failed to upload photo to Firebase Storage.");
            } else {
              throw task.getException();
            }
          }

          return photoRef.getDownloadUrl();
        })
        .continueWithTask(task -> {
          if (!task.isSuccessful()) {
            if (task.getException() == null) {
              throw new Exception("Failed to get download url from Firebase Storage.");
            } else {
              throw task.getException();
            }
          }

          Uri photoUri = task.getResult();
          return mUser.updateProfile(new UserProfileChangeRequest.Builder()
              .setPhotoUri(photoUri)
              .build());
        })
        .addOnCompleteListener(task -> mView.finishUpdateProfile())
        .addOnSuccessListener(v -> mView.finish())
        .addOnFailureListener(error -> {
          Log.e(TAG, "Failed to update profile.", error);
        });
  }

  public void onChangePhotoButtonClicked() {
    Log.d(TAG, "onChangePhotoButtonClicked() called");

    mView.startTakePhotoIntent();
  }
}
