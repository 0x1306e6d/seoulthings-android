package migong.seoulthings.ui.category;

public interface CategoryView {

  int CATEGORY_BICYCLE = 0x00000001;
  int CATEGORY_TOY = 0x00000010;
  int CATEGORY_TOOL = 0x00000100;
  int CATEGORY_MEDICAL_DEVICE = 0x00001000;
  int CATEGORY_POWER_BANK = 0x00010000;
  int CATEGORY_SUIT = 0x00100000;

  void startThingsActivity(int categoryId);
}
