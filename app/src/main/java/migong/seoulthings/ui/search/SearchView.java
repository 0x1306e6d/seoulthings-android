package migong.seoulthings.ui.search;

import java.util.List;

public interface SearchView {

  String KEY_SCOPE = "SCOPE";
  String KEY_CATEGORY = "CATEGORY";
  String SCOPE_THINGS = "SCOPE_THINGS";
  String SCOPE_DONATIONS = "SCOPE_DONATIONS";

  void changeSearchResult(List<SearchResult> searchResults);

  void clearSearchResult();

  void clearQuery();

  void showClearQueryButton();

  void hideClearQueryButton();

  void showProgressBar();

  void hideProgressBar();

  void showEmptyView();

  void hideEmptyView();
}
