package migong.seoulthings.ui.donations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.firestore.Query;
import migong.seoulthings.R;
import migong.seoulthings.ui.donation.DonationActivity;
import migong.seoulthings.ui.donation.DonationView;
import migong.seoulthings.ui.donations.adapter.DonationsRecyclerAdapter;

public class DonationsFragment extends Fragment implements DonationsView {

  private RecyclerView mRecyclerView;
  private DonationsRecyclerAdapter mRecyclerAdapter;

  private Query mQuery;
  private DonationsPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.donations_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupRecycler(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new DonationsPresenter(this);
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
  public void startDonationActivity(@NonNull String donationId) {
    Intent intent = new Intent(getContext(), DonationActivity.class);

    Bundle args = new Bundle();
    args.putString(DonationView.KEY_DONATION_ID, donationId);
    intent.putExtras(args);

    startActivity(intent);
  }

  @Override
  public void setQuery(Query query) {
    mQuery = query;

    if (mRecyclerAdapter != null) {
      mRecyclerAdapter.setQuery(query);
    }
  }

  @Override
  public void startListening() {
    if (mRecyclerAdapter != null) {
      mRecyclerAdapter.startListening();
    }
  }

  @Override
  public void stopListening() {
    if (mRecyclerAdapter != null) {
      mRecyclerAdapter.stopListening();
    }
  }

  private void setupRecycler(@NonNull View view) {
    mRecyclerView = view.findViewById(R.id.donations_recycler);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), GRID_LAYOUT_SPAN_COUNT));
    mRecyclerAdapter = new DonationsRecyclerAdapter(mQuery,
        mPresenter::onRecyclerViewHolderClicked);
    mRecyclerView.setAdapter(mRecyclerAdapter);
  }
}
