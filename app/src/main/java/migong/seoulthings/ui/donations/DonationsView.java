package migong.seoulthings.ui.donations;

import com.google.firebase.firestore.Query;

public interface DonationsView {

  int GRID_LAYOUT_SPAN_COUNT = 3;

  void setQuery(Query query);

  void startListening();

  void stopListening();
}
