package migong.seoulthings.ui.thing;

import android.support.annotation.Nullable;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import migong.seoulthings.data.Review;

public interface ThingView {

  String KEY_THING_ID = "THING_ID";

  void setTitle(String title);

  void setGoogleMap(String title, double latitude, double longitude);

  void setAddress(String address);

  void setContents(String contents);

  void finishLoading();

  void showReviewDialog(@Nullable Review review);

  void addSnapshot(int index, QueryDocumentSnapshot snapshot);

  void modifySnapshot(int oldIndex, int newIndex, QueryDocumentSnapshot snapshot);

  void removeSnapshot(int index);
}
