package migong.seoulthings.ui.things.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import migong.seoulthings.R;
import migong.seoulthings.data.Category;
import migong.seoulthings.data.Thing;

public class ThingsRecyclerViewHolder extends RecyclerView.ViewHolder {

  private TextView mCategoryTextView;
  private TextView mLocationTextView;
  private TextView mContentsTextView;

  public ThingsRecyclerViewHolder(@NonNull View itemView) {
    super(itemView);
    this.mCategoryTextView = itemView.findViewById(R.id.thing_listitem_category);
    this.mLocationTextView = itemView.findViewById(R.id.thing_listitem_location);
    this.mContentsTextView = itemView.findViewById(R.id.thing_listitem_contents);
  }

  public void bind(@NonNull Thing thing) {
    switch (thing.getCategory()) {
      case Category.BICYCLE:
        mCategoryTextView.setText(R.string.bicycle);
        break;
      case Category.MEDICAL_DEVICE:
        mCategoryTextView.setText(R.string.medical_device);
        break;
      case Category.POWER_BANK:
        mCategoryTextView.setText(R.string.power_bank);
        break;
      case Category.SUIT:
        mCategoryTextView.setText(R.string.suit);
        break;
      case Category.TOOL:
        mCategoryTextView.setText(R.string.tool);
        break;
      case Category.TOY:
        mCategoryTextView.setText(R.string.toy);
        break;
      default:
        mCategoryTextView.setText(R.string.msg_loading);
        break;
    }

    if (thing.getLocation() != null) {
      mLocationTextView.setText(thing.getLocation().getName());
    } else {
      mLocationTextView.setText(R.string.msg_loading);
    }

    mContentsTextView.setText(thing.getContents());
  }

  public void clear() {
    mCategoryTextView.setText(R.string.msg_loading);
    mLocationTextView.setText(R.string.msg_loading);
    mContentsTextView.setText(R.string.msg_loading);
  }
}
