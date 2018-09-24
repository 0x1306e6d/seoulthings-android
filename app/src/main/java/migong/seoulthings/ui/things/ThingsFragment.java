package migong.seoulthings.ui.things;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import migong.seoulthings.R;
import migong.seoulthings.ui.things.adapter.ThingsRecyclerAdapter;
import org.apache.commons.lang3.StringUtils;

public class ThingsFragment extends Fragment implements ThingsView {

  private EditText mSearchEditText;
  private ImageButton mSearchButton;
  private ImageButton mClearSearchButton;
  private RecyclerView mRecycler;
  private ThingsRecyclerAdapter mRecyclerAdapter;

  private String mCategory;
  private ThingsViewModel mThingsViewModel;
  private ThingsPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.things_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupToolbar(view);
    setupSearchView(view);
    setupRecycler(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mThingsViewModel = ViewModelProviders.of(this).get(ThingsViewModel.class);

    mPresenter = new ThingsPresenter(this);
    mPresenter.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mCategory = getArguments().getString(ThingsView.KEY_CATEGORY);
      if (mCategory != null) {
        mThingsViewModel.setCategory(mCategory);
      }
    }
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
  public void showSearchView() {
    mSearchEditText.setVisibility(View.VISIBLE);
    mSearchEditText.requestFocus();
    mClearSearchButton.setVisibility(View.VISIBLE);

    hideToolbarTitle();
    showSoftInput(mSearchEditText);
  }

  @Override
  public void hideSearchView() {
    hideSoftInput(mSearchEditText);
    showToolbarTitle();

    mSearchEditText.clearFocus();
    mSearchEditText.setVisibility(View.GONE);
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

    final Toolbar toolbar = view.findViewById(R.id.things_toolbar);
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbar);

    if (activity.getSupportActionBar() == null) {
      return;
    }
    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
    activity.getSupportActionBar().setTitle(mPresenter.getTitleResId(mCategory));
  }

  private void setupRecycler(@NonNull View view) {
    mRecycler = view.findViewById(R.id.things_recycler);
    mRecycler.setHasFixedSize(true);
    mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecycler.addItemDecoration(new DividerItemDecoration(mRecycler.getContext(),
        LinearLayout.VERTICAL));

    mRecyclerAdapter = new ThingsRecyclerAdapter();
    mRecycler.setAdapter(mRecyclerAdapter);
    mThingsViewModel.getThings().observe(this, mRecyclerAdapter::submitList);
  }

  private void setupSearchView(@NonNull View view) {
    mSearchEditText = view.findViewById(R.id.things_search_edittext);

    mSearchButton = view.findViewById(R.id.things_search_button);
    mSearchButton.setOnClickListener(v -> {
      final String query = mSearchEditText.getText().toString();
      mPresenter.onSearchButtonClicked(query);
    });

    mClearSearchButton = view.findViewById(R.id.things_search_clear_button);
    mClearSearchButton.setOnClickListener(v -> {
      final String query = mSearchEditText.getText().toString();
      mPresenter.onClearSearchButtonClicked(query);
    });
  }

  private void showToolbarTitle() {
    if (getActivity() == null) {
      return;
    }

    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    if (activity.getSupportActionBar() == null) {
      return;
    }

    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
  }

  private void hideToolbarTitle() {
    if (getActivity() == null) {
      return;
    }

    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    if (activity.getSupportActionBar() == null) {
      return;
    }

    activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  private void showSoftInput(View view) {
    if (getContext() == null) {
      return;
    }

    InputMethodManager imm = (InputMethodManager) getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm == null) {
      return;
    }

    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
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

    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
  }
}
