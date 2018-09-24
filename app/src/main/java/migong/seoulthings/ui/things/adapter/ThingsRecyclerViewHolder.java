package migong.seoulthings.ui.things.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import migong.seoulthings.R;
import migong.seoulthings.data.Thing;

public class ThingsRecyclerViewHolder extends RecyclerView.ViewHolder {

  private ImageView mIconImageView;
  private TextView mLocationTextView;
  private TextView mContentsTextView;

  public ThingsRecyclerViewHolder(@NonNull View itemView) {
    super(itemView);
    this.mIconImageView = itemView.findViewById(R.id.thing_listitem_icon);
    this.mLocationTextView = itemView.findViewById(R.id.thing_listitem_location);
    this.mContentsTextView = itemView.findViewById(R.id.thing_listitem_contents);
  }

  public void bind(@NonNull Thing thing) {
    mLocationTextView.setText(thing.getLocation().getName());
    mContentsTextView.setText(thing.getContents());
  }

  public void clear() {
    mLocationTextView.setText(R.string.msg_loading);
    mContentsTextView.setText(R.string.msg_loading);
  }
}
