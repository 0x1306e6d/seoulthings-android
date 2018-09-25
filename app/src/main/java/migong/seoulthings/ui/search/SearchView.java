package migong.seoulthings.ui.search;

public interface SearchView {

  String KEY_SCOPE = "SCOPE";

  void clearQuery();

  void showClearQueryButton();

  void hideClearQueryButton();
}
