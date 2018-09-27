package migong.seoulthings.ui.profile.modify;

import android.net.Uri;

public interface ModifyProfileView {

  void finish();

  void changePhoto(Uri photoUri);

  void startTakePhotoIntent();
}
