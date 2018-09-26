package migong.seoulthings.ui.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import migong.seoulthings.R;
import migong.seoulthings.data.Thing;

public class SearchResultRecyclerAdapter
    extends RecyclerView.Adapter<SearchResultRecyclerViewHolder> {

  private List<Thing> mDataSet;

  @NonNull
  @Override
  public SearchResultRecyclerViewHolder onCreateViewHolder(
      @NonNull ViewGroup parent, int viewType) {
    final View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.search_result_listitem, parent, false);
    return new SearchResultRecyclerViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull SearchResultRecyclerViewHolder holder, int position) {
    Thing thing = mDataSet.get(position);
    if (thing == null) {
      return;
    }

    holder.bind(thing.getLocation().getName(), thing.getContents());
  }

  @Override
  public int getItemCount() {
    return mDataSet == null ? 0 : mDataSet.size();
  }

  public void changeDataSet(List<Thing> dataSet) {
    mDataSet = dataSet;
  }
}
