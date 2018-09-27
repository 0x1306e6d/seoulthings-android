package migong.seoulthings.ui.profile.modify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import migong.seoulthings.R;

public class ModifyProfileActivity extends AppCompatActivity implements ModifyProfileView {

  private Button mCompleteButton;

  private ModifyProfilePresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.modify_profile_activity);

    setupAppBar();

    mPresenter = new ModifyProfilePresenter(this);
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

  private void setupAppBar() {
    ImageButton backButton = findViewById(R.id.modify_profile_back_button);
    backButton.setOnClickListener(v -> onBackPressed());

    mCompleteButton = findViewById(R.id.modify_profile_complete_button);
    mCompleteButton.setOnClickListener(v -> mPresenter.onCompleteButtonClicked());
  }
}
