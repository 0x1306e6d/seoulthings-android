package migong.seoulthings.ui.community;

public interface CommunityView {

  int DONATIONS_POSITION = 0;
  int REVIEWS_POSITION = 1;

  void showSearchView();

  void hideSearchView();

  void clearSearchView();
}
