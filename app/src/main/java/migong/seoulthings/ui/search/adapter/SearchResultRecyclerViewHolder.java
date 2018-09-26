package migong.seoulthings.ui.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import migong.seoulthings.R;
import migong.seoulthings.ui.search.SearchResult;

public class SearchResultRecyclerViewHolder extends RecyclerView.ViewHolder {

  public interface ClickListener {

    void onClick(@NonNull String id);

  }

  private ImageView mIconImage;
  private TextView mTitleText;
  private TextView mContentsText;

  private String mId;
  private final ClickListener mClickListener;

  public SearchResultRecyclerViewHolder(@NonNull View itemView, ClickListener clickListener) {
    super(itemView);
    mIconImage = itemView.findViewById(R.id.search_result_listitem_icon);
    mTitleText = itemView.findViewById(R.id.search_result_listitem_title);
    mContentsText = itemView.findViewById(R.id.search_result_listitem_contents);
    mClickListener = clickListener;

    itemView.setOnClickListener(v -> {
      if (mClickListener != null && mId != null) {
        mClickListener.onClick(mId);
      }
    });
  }

  public void bind(@NonNull SearchResult result) {
    mId = result.getId();
    mTitleText.setText(result.getTitle());
    mContentsText.setText(result.getContents());
  }

  public void clear() {
    mId = null;
    mTitleText.setText(R.string.msg_loading);
    mContentsText.setText(R.string.msg_loading);
  }
}
