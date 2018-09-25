package migong.seoulthings.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public class SearchActivity extends AppCompatActivity implements SearchView {

  private ImageButton mBackButton;
  private EditText mQueryEditText;
  private ImageButton mClearQueryButton;

  private String mScope;
  private SearchPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_activity);

    final Intent intent = getIntent();
    final Bundle args = intent.getExtras();
    if (args == null) {
      finish();
      return;
    }

    mScope = args.getString(KEY_SCOPE);
    if (StringUtils.isEmpty(mScope)) {
      finish();
      return;
    }

    setupQueryView();

    mPresenter = new SearchPresenter(this, mScope);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();

    mQueryEditText.requestFocus();
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

  private void setupQueryView() {
    mBackButton = findViewById(R.id.search_back_button);
    mBackButton.setOnClickListener(v -> onBackPressed());

    mQueryEditText = findViewById(R.id.search_query_edittext);
    mQueryEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        if (s == null) {
          return;
        }

        mPresenter.onQueryChanged(s.toString());
      }
    });
    mClearQueryButton = findViewById(R.id.search_clear_query_button);
    mClearQueryButton.setOnClickListener(v -> mPresenter.onClearQueryButtonClicked());
  }

  @Override
  public void clearQuery() {
    mQueryEditText.setText(StringUtils.EMPTY);
  }

  @Override
  public void showClearQueryButton() {
    mClearQueryButton.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideClearQueryButton() {
    mClearQueryButton.setVisibility(View.GONE);
  }
}
