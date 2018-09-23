package migong.seoulthings.ui.things;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.LinearLayout;
import migong.seoulthings.R;
import migong.seoulthings.ui.things.adapter.ThingsRecyclerAdapter;

public class ThingsFragment extends Fragment implements ThingsView {

  private RecyclerView mRecycler;
  private ThingsRecyclerAdapter mRecyclerAdapter;

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
    setupRecycler(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mThingsViewModel = ViewModelProviders.of(this).get(ThingsViewModel.class);

    mPresenter = new ThingsPresenter(this);
    mPresenter.onCreate(savedInstanceState);

    if (getArguments() != null) {
      String category = getArguments().getString(ThingsView.KEY_CATEGORY);
      mThingsViewModel.setCategory(category);
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
}
