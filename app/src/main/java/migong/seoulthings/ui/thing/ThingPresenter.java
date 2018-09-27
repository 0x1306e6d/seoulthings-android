package migong.seoulthings.ui.thing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
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
  private FirebaseFirestore mFirestore;
  private Query mQuery;
  private ListenerRegistration mRegistration;
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

    mFirestore = FirebaseFirestore.getInstance();
    mFirestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build());
    mQuery = mFirestore.collection("reviews")
        .whereEqualTo("thingId", mThingId)
        .orderBy("updatedAt", Direction.DESCENDING);
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

    startListening();
  }

  @Override
  public void onPause() {
    Log.d(TAG, "onPause() called");

    stopListening();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy() called");
  }

  public void onMakeReviewSuggestionClicked() {
    mView.showReviewDialog();
  }

  public void createReview(float rating, String contents) {
    Log.d(TAG,
        "createReview() called with: rating = [" + rating + "], contents = [" + contents + "]");
  }

  private void startListening() {
    if (mQuery != null && mRegistration == null) {
      mRegistration = mQuery.addSnapshotListener((snapshot, e) -> {
        if (e != null) {
          Log.e(TAG, "onEvent: error", e);
          return;
        }

        if (snapshot == null) {
          Log.e(TAG, "onEvent: snapshot is NULL.");
          return;
        }

        Log.d(TAG, "onEvent: numChanges is " + snapshot.getDocumentChanges().size());
        for (DocumentChange change : snapshot.getDocumentChanges()) {
          switch (change.getType()) {
            case ADDED:
              onDocumentAdded(change);
              break;
            case MODIFIED:
              onDocumentModified(change);
              break;
            case REMOVED:
              onDocumentRemoved(change);
              break;
          }
        }
      });
    }
  }

  private void stopListening() {
    if (mRegistration != null) {
      mRegistration.remove();
      mRegistration = null;
    }
  }

  private void onDocumentAdded(DocumentChange change) {
    Log.d(TAG, "onDocumentAdded() called with: change = [" + change + "]");

    mView.addSnapshot(change.getNewIndex(), change.getDocument());
  }

  private void onDocumentModified(DocumentChange change) {
    Log.d(TAG, "onDocumentModified() called with: change = [" + change + "]");

    mView.modifySnapshot(change.getOldIndex(), change.getNewIndex(), change.getDocument());
  }

  private void onDocumentRemoved(DocumentChange change) {
    Log.d(TAG, "onDocumentRemoved() called with: change = [" + change + "]");

    mView.removeSnapshot(change.getOldIndex());
  }
}
