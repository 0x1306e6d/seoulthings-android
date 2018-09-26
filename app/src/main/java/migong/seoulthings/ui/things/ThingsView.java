package migong.seoulthings.ui.things;

import android.support.annotation.NonNull;

public interface ThingsView {

  String KEY_CATEGORY = "CATEGORY";

  void startSearchActivity();

  void startThingActivity(@NonNull String thingId);
}
