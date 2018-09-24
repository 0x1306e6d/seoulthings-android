package migong.seoulthings.ui.things;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import migong.seoulthings.R;
import migong.seoulthings.data.Category;
import migong.seoulthings.ui.Presenter;
import org.apache.commons.lang3.StringUtils;

public class ThingsPresenter implements Presenter {

  private static final String TAG = ThingsPresenter.class.getSimpleName();

  private boolean mSearching;
  @NonNull
  private final ThingsView mView;

  public ThingsPresenter(@NonNull ThingsView view) {
    this.mView = view;
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

  public void onSearchButtonClicked(String query) {
    Log.d(TAG, "onSearchButtonClicked() called with: query = [" + query + "]");

    if (mSearching) {
      // TODO(@gihwan): 검색
    } else {
      mSearching = true;
      mView.showSearchView();
    }
  }

  public void onClearSearchButtonClicked(String query) {
    Log.d(TAG, "onClearSearchButtonClicked() called with: query = [" + query + "]");

    if (StringUtils.isEmpty(query)) {
      mView.hideSearchView();
      mSearching = false;
    } else {
      mView.clearSearchView();
    }
  }

  @StringRes
  public int getTitleResId(String category) {
    switch (category) {
      case Category.BICYCLE:
        return R.string.bicycle;
      case Category.MEDICAL_DEVICE:
        return R.string.medical_device;
      case Category.POWER_BANK:
        return R.string.power_bank;
      case Category.SUIT:
        return R.string.suit;
      case Category.TOOL:
        return R.string.tool;
      case Category.TOY:
        return R.string.toy;
      default:
        return R.string.msg_loading;
    }
  }
}
