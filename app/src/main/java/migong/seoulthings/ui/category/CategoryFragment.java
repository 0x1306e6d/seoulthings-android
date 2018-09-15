package migong.seoulthings.ui.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import migong.seoulthings.R;

public class CategoryFragment extends Fragment implements CategoryView {

  private TextInputEditText mSearchEditText;
  private Button mCategory1Button;
  private Button mCategory2Button;
  private Button mCategory3Button;
  private Button mCategory4Button;
  private Button mCategory5Button;
  private Button mCategory6Button;

  private CategoryPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.category_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mSearchEditText = view.findViewById(R.id.category_search_edittext);
    mCategory1Button = view.findViewById(R.id.category_button_1);
    mCategory2Button = view.findViewById(R.id.category_button_2);
    mCategory3Button = view.findViewById(R.id.category_button_3);
    mCategory4Button = view.findViewById(R.id.category_button_4);
    mCategory5Button = view.findViewById(R.id.category_button_5);
    mCategory6Button = view.findViewById(R.id.category_button_6);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new CategoryPresenter(this);
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
}
