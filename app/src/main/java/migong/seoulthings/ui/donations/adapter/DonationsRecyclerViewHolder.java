package migong.seoulthings.ui.donations.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentSnapshot;
import migong.seoulthings.R;
import migong.seoulthings.data.Donation;

public class DonationsRecyclerViewHolder extends ViewHolder {

  private static final String TAG = DonationsRecyclerViewHolder.class.getSimpleName();

  private TextView mTitleView;
  private TextView mLocationView;

  public DonationsRecyclerViewHolder(@NonNull View itemView) {
    super(itemView);
    mTitleView = itemView.findViewById(R.id.donation_listitem_title);
    mLocationView = itemView.findViewById(R.id.donation_listitem_location);
  }

  public void bind(final DocumentSnapshot snapshot) {
    final Donation donation = snapshot.toObject(Donation.class);
    if (donation == null) {
      Log.e(TAG, "bind: donation is NULL.");
      return;
    }

    mTitleView.setText(donation.getTitle());
    mLocationView.setText(donation.getLocation());
  }
}
