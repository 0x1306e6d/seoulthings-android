package migong.seoulthings.ui.signin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import migong.seoulthings.R;

public class SignInActivity extends AppCompatActivity implements SignInView {

  private SignInPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signin_activity);

    mPresenter = new SignInPresenter(this);
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
