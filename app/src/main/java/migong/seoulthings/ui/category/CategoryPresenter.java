package migong.seoulthings.ui.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import migong.seoulthings.ui.Presenter;

public class CategoryPresenter implements Presenter {

  private static final String TAG = CategoryPresenter.class.getSimpleName();

  @NonNull
  private final CategoryView mView;

  public CategoryPresenter(@NonNull CategoryView view) {
    this.mView = view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
  }

  @Override
  public void onResume() {
    Log.d(TAG, "onResume() called");
  }

  @Override
  public void onPause() {
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy() called");
  }

  public void onCategoryButtonClicked(int categoryId) {
    switch (categoryId) {
      case CategoryView.CATEGORY_BICYCLE:
      case CategoryView.CATEGORY_TOY:
      case CategoryView.CATEGORY_TOOL:
      case CategoryView.CATEGORY_MEDICAL_DEVICE:
      case CategoryView.CATEGORY_POWER_BANK:
      case CategoryView.CATEGORY_SUIT:
        mView.startThingsActivity(categoryId);
        break;
      default:
        // TODO(@gihwan): 올바르지 않은 카테고리
        break;
    }
  }
}
