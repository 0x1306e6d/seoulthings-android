package migong.seoulthings.ui.donation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;

public class DonationPresenter implements Presenter {

  private static final String TAG = DonationPresenter.class.getSimpleName();

  @NonNull
  private final String mDonationId;
  @NonNull
  private final DonationView mView;

  public DonationPresenter(@NonNull DonationView view, @NonNull String donationId) {
    this.mView = view;
    this.mDonationId = donationId;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    Log.d(TAG, "onCreate: donationId is " + mDonationId);
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
}
