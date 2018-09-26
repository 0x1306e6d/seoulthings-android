package migong.seoulthings.ui.profile.things;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class MyThingsFragment extends Fragment implements MyThingsView {

  @NonNull
  private MyThingsPresenter mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new MyThingsPresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }
}
