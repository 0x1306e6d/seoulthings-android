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
import migong.seoulthings.ui.Presenter;

public class ModifyProfilePresenter implements Presenter {

  private static final String TAG = ModifyProfilePresenter.class.getSimpleName();

  private FirebaseAuth mAuth;
  private FirebaseUser mUser;
  private Uri mCurrentPhotoUri;
  @NonNull
  private final ModifyProfileView mView;

  public ModifyProfilePresenter(@NonNull ModifyProfileView view) {
    mView = view;
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
    mCurrentPhotoUri = photoUri;
    mView.changePhoto(photoUri);
  }

  public void onCompleteButtonClicked() {
    Log.d(TAG, "onCompleteButtonClicked() called");
    if (mUser == null) {
      return;
    }

    final UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
        .setPhotoUri(mCurrentPhotoUri)
        .build();
    mUser.updateProfile(request)
        .addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
            mView.finish();
          } else {
            Log.e(TAG, "Failed to update profile.", task.getException());
          }
        });
  }

  public void onChangePhotoButtonClicked() {
    Log.d(TAG, "onChangePhotoButtonClicked() called");

    mView.startTakePhotoIntent();
  }
}
