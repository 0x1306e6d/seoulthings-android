package migong.seoulthings.ui.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import migong.seoulthings.R;

public class SearchResultRecyclerViewHolder extends RecyclerView.ViewHolder {

  private ImageView mIconImage;
  private TextView mTitleText;
  private TextView mContentsText;

  public SearchResultRecyclerViewHolder(@NonNull View itemView) {
    super(itemView);
    mIconImage = itemView.findViewById(R.id.search_result_listitem_icon);
    mTitleText = itemView.findViewById(R.id.search_result_listitem_title);
    mContentsText = itemView.findViewById(R.id.search_result_listitem_contents);
  }

  public void bind(String title, String contents) {
    mTitleText.setText(title);
    mContentsText.setText(contents);
  }

  public void clear() {
    mTitleText.setText(R.string.msg_loading);
    mContentsText.setText(R.string.msg_loading);
  }
}
