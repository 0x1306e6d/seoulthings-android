package migong.seoulthings.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;
import org.apache.commons.lang3.StringUtils;

public class SearchPresenter implements Presenter {

  private static final String TAG = SearchPresenter.class.getSimpleName();

  @NonNull
  private final String mScope;
  @NonNull
  private final SearchView mView;

  public SearchPresenter(@NonNull SearchView view, @NonNull String scope) {
    this.mView = view;
    this.mScope = scope;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    Log.d(TAG, "onCreate: scope is " + mScope);
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

  public void onQueryChanged(String query) {
    if (StringUtils.isEmpty(query)) {
      mView.hideClearQueryButton();
    } else {
      mView.showClearQueryButton();
    }
  }

  public void onClearQueryButtonClicked() {
    mView.clearQuery();
  }
}
