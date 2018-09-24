package migong.seoulthings.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import migong.seoulthings.R;
import migong.seoulthings.data.Category;
import migong.seoulthings.ui.things.ThingsActivity;
import migong.seoulthings.ui.things.ThingsView;
import org.apache.commons.lang3.StringUtils;

public class CategoryFragment extends Fragment implements CategoryView {

  private EditText mSearchEditText;
  private ImageButton mSearchButton;
  private ImageButton mClearSearchButton;

  private Button mBicycleCategoryButton;
  private Button mToyCategoryButton;
  private Button mToolCategoryButton;
  private Button mMedicalDeviceCategoryButton;
  private Button mPowerBankCategoryButton;
  private Button mSuitCategoryButton;

  private CategoryPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.category_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupToolbar(view);
    setupSearchView(view);

    mBicycleCategoryButton = view.findViewById(R.id.category_button_bicycle);
    mBicycleCategoryButton.setOnClickListener(v -> {
      mPresenter.onCategoryButtonClicked(Category.BICYCLE);
    });

    mToyCategoryButton = view.findViewById(R.id.category_button_toy);
    mToyCategoryButton.setOnClickListener(v -> {
      mPresenter.onCategoryButtonClicked(Category.TOY);
    });

    mToolCategoryButton = view.findViewById(R.id.category_button_tool);
    mToolCategoryButton.setOnClickListener(v -> {
      mPresenter.onCategoryButtonClicked(Category.TOOL);
    });

    mMedicalDeviceCategoryButton = view.findViewById(R.id.category_button_medical_device);
    mMedicalDeviceCategoryButton.setOnClickListener(v -> {
      mPresenter.onCategoryButtonClicked(Category.MEDICAL_DEVICE);
    });

    mPowerBankCategoryButton = view.findViewById(R.id.category_button_power_bank);
    mPowerBankCategoryButton.setOnClickListener(v -> {
      mPresenter.onCategoryButtonClicked(Category.POWER_BANK);
    });

    mSuitCategoryButton = view.findViewById(R.id.category_button_suit);
    mSuitCategoryButton.setOnClickListener(v -> {
      mPresenter.onCategoryButtonClicked(Category.SUIT);
    });
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new CategoryPresenter(this);
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

  @Override
  public void startThingsActivity(@NonNull String category) {
    Intent intent = new Intent(getActivity(), ThingsActivity.class);

    Bundle args = new Bundle();
    args.putString(ThingsView.KEY_CATEGORY, category);
    intent.putExtras(args);

    startActivity(intent);
  }

  @Override
  public void showSearchView() {
    mClearSearchButton.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideSearchView() {
    hideSoftInput(mSearchEditText);

    mSearchEditText.clearFocus();
    mClearSearchButton.setVisibility(View.GONE);
  }

  @Override
  public void clearSearchView() {
    mSearchEditText.setText(StringUtils.EMPTY);
  }

  private void setupToolbar(@NonNull View view) {
    if (getActivity() == null) {
      return;
    }

    final Toolbar toolbar = view.findViewById(R.id.category_toolbar);
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);

    if (activity.getSupportActionBar() == null) {
      return;
    }
    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  private void setupSearchView(@NonNull View view) {
    mSearchEditText = view.findViewById(R.id.category_search_edittext);
    mSearchEditText.setOnFocusChangeListener(mPresenter::onSearchViewFocusChanged);

    mSearchButton = view.findViewById(R.id.category_search_button);
    mSearchButton.setOnClickListener(v -> {
      final String query = mSearchEditText.getText().toString();
      mPresenter.onSearchButtonClicked(query);
    });

    mClearSearchButton = view.findViewById(R.id.category_search_clear_button);
    mClearSearchButton.setOnClickListener(v -> {
      final String query = mSearchEditText.getText().toString();
      mPresenter.onClearSearchButtonClicked(query);
    });
  }

  private void hideSoftInput(View view) {
    if (getContext() == null) {
      return;
    }

    InputMethodManager imm = (InputMethodManager) getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm == null) {
      return;
    }

    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
