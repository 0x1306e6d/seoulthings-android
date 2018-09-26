package migong.seoulthings.ui.donation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public class DonationActivity extends AppCompatActivity implements DonationView {

  private DonationPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.donation_activity);

    final Intent intent = getIntent();
    final Bundle args = intent.getExtras();
    if (args == null) {
      finish();
      return;
    }

    final String donationId = args.getString(DonationView.KEY_DONATION_ID);
    if (StringUtils.isEmpty(donationId)) {
      finish();
      return;
    }

    mPresenter = new DonationPresenter(this, donationId);
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
