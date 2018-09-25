package migong.seoulthings.ui.community;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import migong.seoulthings.R;
import migong.seoulthings.ui.community.adapter.CommunityPagerAdapter;
import org.apache.commons.lang3.StringUtils;

public class CommunityFragment extends Fragment implements CommunityView {

  private TextView mTitleText;
  private EditText mSearchEditText;
  private ImageButton mSearchButton;
  private ImageButton mClearSearchButton;
  private ViewPager mViewPager;
  private TabLayout mTabLayout;
  private CommunityPagerAdapter mPagerAdapter;
  private FloatingActionButton mFAB;

  private CommunityPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.community_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupTitle(view);
    setupSearchView(view);
    setupTab(view);
    setupFAB(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new CommunityPresenter(this);
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
  public void showSearchView() {
    mSearchEditText.setVisibility(View.VISIBLE);
    mSearchEditText.requestFocus();
    mClearSearchButton.setVisibility(View.VISIBLE);

    hideTitle();
    showSoftInput(mSearchEditText);
  }

  @Override
  public void hideSearchView() {
    hideSoftInput(mSearchEditText);
    showTitle();

    mSearchEditText.clearFocus();
    mSearchEditText.setVisibility(View.GONE);
    mClearSearchButton.setVisibility(View.GONE);
  }

  @Override
  public void clearSearchView() {
    mSearchEditText.setText(StringUtils.EMPTY);
  }

  private void setupTitle(@NonNull View view) {
    mTitleText = view.findViewById(R.id.community_title);
  }

  private void setupSearchView(@NonNull View view) {
    mSearchEditText = view.findViewById(R.id.community_search_edittext);

    mSearchButton = view.findViewById(R.id.community_search_button);
    mSearchButton.setOnClickListener(v -> {
      final String query = mSearchEditText.getText().toString();
      mPresenter.onSearchButtonClicked(query);
    });

    mClearSearchButton = view.findViewById(R.id.community_search_clear_button);
    mClearSearchButton.setOnClickListener(v -> {
      final String query = mSearchEditText.getText().toString();
      mPresenter.onClearSearchButtonClicked(query);
    });
  }

  private void setupTab(@NonNull View view) {
    mViewPager = view.findViewById(R.id.community_viewpager);
    mTabLayout = view.findViewById(R.id.community_tablayout);
    mPagerAdapter = new CommunityPagerAdapter(getChildFragmentManager());
    mViewPager.setAdapter(mPagerAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  private void setupFAB(@NonNull View view) {
    mFAB = view.findViewById(R.id.community_fab);
    mFAB.setOnClickListener(v -> {
      if (mViewPager == null) {
        return;
      }

      mPresenter.onFABClicked(mViewPager.getCurrentItem());
    });
  }

  private void showTitle() {
    if (mTitleText == null) {
      return;
    }

    mTitleText.setVisibility(View.VISIBLE);
  }

  private void hideTitle() {
    if (mTitleText == null) {
      return;
    }

    mTitleText.setVisibility(View.GONE);
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
