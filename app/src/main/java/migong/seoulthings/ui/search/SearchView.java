package migong.seoulthings.ui.search;

import java.util.List;
import migong.seoulthings.data.Thing;

public interface SearchView {

  String KEY_SCOPE = "SCOPE";
  String KEY_CATEGORY = "CATEGORY";
  String SCOPE_THINGS = "SCOPE_THINGS";
  String SCOPE_DONATIONS = "SCOPE_DONATIONS";

  void changeSearchResult(List<Thing> searchResult);

  void clearSearchResult();

  void clearQuery();

  void showClearQueryButton();

  void hideClearQueryButton();
}
