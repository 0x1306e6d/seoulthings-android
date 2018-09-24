package migong.seoulthings.ui.category;

import android.support.annotation.NonNull;

public interface CategoryView {

  void startThingsActivity(@NonNull String category);

  void showSearchView();

  void hideSearchView();

  void clearSearchView();
}
