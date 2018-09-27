package migong.seoulthings.ui.thing;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public interface ThingView {

  String KEY_THING_ID = "THING_ID";

  void setTitle(String title);

  void setGoogleMap(String title, double latitude, double longitude);

  void setAddress(String address);

  void setContents(String contents);

  void finishLoading();

  void showReviewDialog();

  void addSnapshot(int index, QueryDocumentSnapshot snapshot);

  void modifySnapshot(int oldIndex, int newIndex, QueryDocumentSnapshot snapshot);

  void removeSnapshot(int index);
}
