package migong.seoulthings.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import migong.seoulthings.SeoulThingsConstants;
import migong.seoulthings.api.SearchAPI;
import migong.seoulthings.data.Category;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchThingsPresenter extends SearchPresenter {

  private static final String TAG = SearchThingsPresenter.class.getSimpleName();

  private Retrofit mRetrofit;
  private SearchAPI mSearchAPI;
  @Nullable
  private final String mCategory;
  @NonNull
  private final CompositeDisposable mCompositeDisposable;

  public SearchThingsPresenter(@NonNull SearchView view, @Nullable String category) {
    super(view);
    this.mCategory = category;
    this.mCompositeDisposable = new CompositeDisposable();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    Log.d(TAG, "onCreate: category is " + mCategory);

    mRetrofit = new Retrofit.Builder()
        .baseUrl(SeoulThingsConstants.SEOULTHINGS_SERVER_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    mSearchAPI = mRetrofit.create(SearchAPI.class);
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

  @Override
  protected void search(String query) {
    Log.d(TAG, "search() called with: query = [" + query + "]");
    mCompositeDisposable.clear();

    if (StringUtils.isEmpty(query)) {
      mView.clearSearchResult();
      return;
    }

    if (StringUtils.isEmpty(mCategory) || StringUtils.equals(Category.ALL, mCategory)) {
      mCompositeDisposable.add(
          mSearchAPI.searchThings(query)
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
              .subscribe(
                  response -> {
                    Log.d(TAG, "search: response is " + response);
                    mView.changeSearchResult(response.getThings());
                  },
                  error -> {
                    Log.e(TAG, "Failed to search things.", error);
                  }
              )
      );
    } else {
      mCompositeDisposable.add(
          mSearchAPI.searchThings(mCategory, query)
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
              .subscribe(
                  response -> {
                    Log.d(TAG, "search: response is " + response);
                    mView.changeSearchResult(response.getThings());
                  },
                  error -> {
                    Log.e(TAG, "Failed to search " + mCategory + " things.", error);
                  }
              )
      );
    }
  }
}
