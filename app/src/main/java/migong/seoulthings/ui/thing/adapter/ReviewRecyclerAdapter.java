package migong.seoulthings.ui.thing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;
import migong.seoulthings.R;
import migong.seoulthings.SeoulThingsConstants;
import migong.seoulthings.api.FirebaseAPI;
import migong.seoulthings.data.Review;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerViewHolder> {

  @NonNull
  private ReviewRecyclerFormViewHolder.ClickListener mFormViewHolderClickListener;
  @NonNull
  private final FirebaseAuth mAuth;
  private final FirebaseUser mUser;
  @NonNull
  private final Retrofit mRetrofit;
  @NonNull
  private final FirebaseAPI mFirebaseAPI;
  @NonNull
  private final CompositeDisposable mCompositeDisposable;
  @NonNull
  private final List<DocumentSnapshot> mSnapshots;

  public ReviewRecyclerAdapter(
      @NonNull ReviewRecyclerFormViewHolder.ClickListener formViewHolderClickListener) {
    super();
    mFormViewHolderClickListener = formViewHolderClickListener;
    mAuth = FirebaseAuth.getInstance();
    mUser = mAuth.getCurrentUser();
    mRetrofit = new Retrofit.Builder()
        .baseUrl(SeoulThingsConstants.SEOULTHINGS_SERVER_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    mFirebaseAPI = mRetrofit.create(FirebaseAPI.class);
    mCompositeDisposable = new CompositeDisposable();
    mSnapshots = new ArrayList<>();
  }

  @NonNull
  @Override
  public ReviewRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == ReviewRecyclerViewHolder.VIEW_TYPE_FORM) {
      final View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.thing_reviews_listitem_form, parent, false);
      return new ReviewRecyclerFormViewHolder(view, mUser, mFormViewHolderClickListener);
    } else {
      final View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.thing_reviews_listitem_review, parent, false);
      return new ReviewRecyclerReviewViewHolder(view, mFirebaseAPI, mCompositeDisposable);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewRecyclerViewHolder holder, int position) {
    if (position > 0) {
      final Review review = getSnapshot(position).toObject(Review.class);
      Log.d("ReviewRecyclerAdapter", "onBindViewHolder: review is " + review);
      if (review != null) {
        holder.bind(review);
      } else {
        holder.clear();
      }
    }
  }

  @Override
  public int getItemCount() {
    return mSnapshots.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return ReviewRecyclerViewHolder.VIEW_TYPE_FORM;
    } else {
      return ReviewRecyclerViewHolder.VIEW_TYPE_REVIEW;
    }
  }

  public void dispose() {
    mCompositeDisposable.dispose();
  }

  public void addSnapshot(int index, QueryDocumentSnapshot snapshot) {
    mSnapshots.add(index, snapshot);
    notifyItemInserted(index);
  }

  public void modifySnapshot(int oldIndex, int newIndex, QueryDocumentSnapshot snapshot) {
    if (oldIndex == newIndex) {
      mSnapshots.set(oldIndex, snapshot);
      notifyItemChanged(oldIndex);
    } else {
      mSnapshots.remove(oldIndex);
      mSnapshots.add(newIndex, snapshot);
      notifyItemMoved(oldIndex, newIndex);
    }
  }

  public void removeSnapshot(int index) {
    mSnapshots.remove(index);
    notifyItemRemoved(index);
  }

  private DocumentSnapshot getSnapshot(int position) {
    return mSnapshots.get(position - 1);
  }
}
