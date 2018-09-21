package migong.seoulthings.ui.things;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import migong.seoulthings.R;

public class ThingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.things_activity);

    final Intent intent = getIntent();
    final Bundle args = intent.getExtras();
    Fragment thingsFragment = new ThingsFragment();
    thingsFragment.setArguments(args);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.things_activity, thingsFragment)
        .commit();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
