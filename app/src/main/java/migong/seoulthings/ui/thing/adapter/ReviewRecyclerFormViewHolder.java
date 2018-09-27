package migong.seoulthings.ui.thing.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import migong.seoulthings.data.Review;

public class ReviewRecyclerFormViewHolder extends ReviewRecyclerViewHolder {

  public interface ClickListener {

    void onClick();

  }

  @NonNull
  private ClickListener mClickListener;

  public ReviewRecyclerFormViewHolder(@NonNull View itemView,
      @NonNull ClickListener clickListener) {
    super(itemView);
    mClickListener = clickListener;

    itemView.setOnClickListener(v -> mClickListener.onClick());
  }

  @Override
  public void bind(@NonNull Review review) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }
}
