package migong.seoulthings.ui.reviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import migong.seoulthings.R;

public class ReviewsFragment extends Fragment implements ReviewsView {

  private RecyclerView mRecyclerView;

  private ReviewsPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.reviews_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupRecycler(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new ReviewsPresenter(this);
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

  private void setupRecycler(@NonNull View view) {
    mRecyclerView = view.findViewById(R.id.reviews_recycler);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),
        LinearLayout.VERTICAL));
  }
}
