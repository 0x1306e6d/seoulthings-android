package migong.seoulthings.ui.thing.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import migong.seoulthings.R;
import migong.seoulthings.api.FirebaseAPI;
import migong.seoulthings.data.Review;
import org.apache.commons.lang3.StringUtils;

public class ReviewRecyclerReviewViewHolder extends ReviewRecyclerViewHolder {

  private static final String TAG = ReviewRecyclerReviewViewHolder.class.getSimpleName();
  @SuppressLint("SimpleDateFormat")
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

  private ContentLoadingProgressBar mLoadingProgressBar;
  private ImageView mProfilePhotoImage;
  private TextView mProfileDisplayNameText;
  private TextView mUpdatedAtText;
  private TextView mContentsText;

  @NonNull
  private final FirebaseAPI mFirebaseAPI;
  @NonNull
  private final CompositeDisposable mCompositeDisposable;

  public ReviewRecyclerReviewViewHolder(@NonNull View itemView, @NonNull FirebaseAPI firebaseAPI,
      @NonNull CompositeDisposable compositeDisposable) {
    super(itemView);
    mLoadingProgressBar = itemView.findViewById(R.id.review_listitem_loading_progressbar);
    mProfilePhotoImage = itemView.findViewById(R.id.review_listitem_profile_photo);
    mProfileDisplayNameText = itemView.findViewById(R.id.review_listitem_profile_display_name);
    mUpdatedAtText = itemView.findViewById(R.id.review_listitem_updated_at);
    mContentsText = itemView.findViewById(R.id.review_listitem_contents);

    mFirebaseAPI = firebaseAPI;
    mCompositeDisposable = compositeDisposable;
    mLoadingProgressBar.show();
  }

  @Override
  public void bind(@NonNull Review review) {
    Log.d(TAG, "bind() called with: review = [" + review + "]");

    mCompositeDisposable.add(
        mFirebaseAPI.getFirebaseUser(review.getAuthorId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                user -> {
                  if (user == null) {
                    Log.e(TAG, "bind: user is NULL.");
                    return;
                  }
                  finishLoading();

                  mProfileDisplayNameText.setText(user.getDisplayName());
                  mUpdatedAtText.setText(DATE_FORMAT.format(review.getUpdatedAt().toDate()));
                  mContentsText.setText(review.getContents());

                  if (StringUtils.isNotEmpty(user.getPhotoURL())) {
                    Uri uri = Uri.parse(user.getPhotoURL());
                    Picasso.get()
                        .load(uri)
                        .into(mProfilePhotoImage);
                  }
                },
                error -> {
                  Log.e(TAG, "Failed to get user.", error);
                }
            )
    );
  }

  @Override
  public void clear() {
    mLoadingProgressBar.show();

    mProfilePhotoImage.setVisibility(View.GONE);
    mProfileDisplayNameText.setVisibility(View.GONE);
    mUpdatedAtText.setVisibility(View.GONE);
    mContentsText.setVisibility(View.GONE);
  }

  private void finishLoading() {
    mLoadingProgressBar.hide();

    mProfilePhotoImage.setVisibility(View.VISIBLE);
    mProfileDisplayNameText.setVisibility(View.VISIBLE);
    mUpdatedAtText.setVisibility(View.VISIBLE);
    mContentsText.setVisibility(View.VISIBLE);
  }
}
