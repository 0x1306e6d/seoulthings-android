package migong.seoulthings.ui.launcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import migong.seoulthings.R;

public class LauncherActivity extends AppCompatActivity implements LauncherView {

  private LauncherPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.launcher_activity);

    mPresenter = new LauncherPresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }
}
