package migong.seoulthings.ui.thing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import migong.seoulthings.SeoulThingsConstants;
import migong.seoulthings.api.ThingAPI;
import migong.seoulthings.data.Thing;
import migong.seoulthings.ui.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThingPresenter implements Presenter {

  private static final String TAG = ThingPresenter.class.getSimpleName();

  private Retrofit mRetrofit;
  private ThingAPI mThingAPI;
  private Thing mThing;
  @NonNull
  private final String mThingId;
  @NonNull
  private final CompositeDisposable mCompositeDisposable;
  @NonNull
  private final ThingView mView;

  public ThingPresenter(@NonNull ThingView view, @NonNull String thingId) {
    mView = view;
    mThingId = thingId;
    mCompositeDisposable = new CompositeDisposable();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    Log.d(TAG, "onCreate: thingId is " + mThingId);

    mRetrofit = new Retrofit.Builder()
        .baseUrl(SeoulThingsConstants.SEOULTHINGS_SERVER_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    mThingAPI = mRetrofit.create(ThingAPI.class);
  }

  @Override
  public void onResume() {
    Log.d(TAG, "onResume() called");

    mCompositeDisposable.add(
        mThingAPI.getThing(mThingId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                response -> {
                  final int size = response.getSize();
                  final Thing thing = response.getThing();

                  if (size == 0 || thing == null) {
                    Log.e(TAG, "Failed to get a thing of id " + mThingAPI);
                  } else {
                    mThing = thing;
                    mView.finishLoading();

                    mView.setTitle(mThing.getLocation().getName());

                    if (mThing.getLocation().getLatitude() != null &&
                        mThing.getLocation().getLongitude() != null) {
                      final double latitude = mThing.getLocation().getLatitude();
                      final double longitude = mThing.getLocation().getLongitude();
                      mView.setGoogleMap(mThing.getLocation().getName(), latitude, longitude);
                    }

                    mView.setAddress(mThing.getLocation().getAddress());
                    mView.setContents(mThing.getContents());
                  }
                },
                error -> {
                  Log.e(TAG, "Failed to get a thing.", error);
                }
            )
    );
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
