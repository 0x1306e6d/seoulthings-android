package migong.seoulthings.ui.thing;

public interface ThingView {

  String KEY_THING_ID = "THING_ID";

  void setTitle(String title);

  void setGoogleMap(String title, double latitude, double longitude);

  void finishLoading();
}
