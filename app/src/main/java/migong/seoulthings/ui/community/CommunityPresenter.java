package migong.seoulthings.ui.community;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;
import org.apache.commons.lang3.StringUtils;

public class CommunityPresenter implements Presenter {

  private static final String TAG = CommunityPresenter.class.getSimpleName();

  private boolean mSearching;
  @NonNull
  private CommunityView mView;

  public CommunityPresenter(@NonNull CommunityView view) {
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

  public void onFABClicked(int currentItem) {
    Log.d(TAG, "onFABClicked() called with: currentItem = [" + currentItem + "]");
  }
}
