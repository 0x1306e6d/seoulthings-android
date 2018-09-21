package migong.seoulthings.ui.things;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import migong.seoulthings.R;

public class ThingsFragment extends Fragment implements ThingsView {

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
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new ThingsPresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    mPresenter.onResume();

    if (getArguments() != null) {
      int categoryId = getArguments().getInt(ThingsView.KEY_CATEGORY_ID);
      mPresenter.setCategoryId(categoryId);
    }
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
    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
  }
}
