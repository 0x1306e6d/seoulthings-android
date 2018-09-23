package migong.seoulthings.data;

import org.apache.commons.lang3.StringUtils;

public final class Category {

  public static final String BICYCLE = "BICYCLE";
  public static final String MEDICAL_DEVICE = "MEDICALDEVICE";
  public static final String POWER_BANK = "POWERBANK";
  public static final String SUIT = "SUIT";
  public static final String TOOL = "TOOL";
  public static final String TOY = "TOY";

  public static boolean isValidCategory(String category) {
    if (category == null) {
      return false;
    }
    return StringUtils.equalsAny(category, BICYCLE, MEDICAL_DEVICE, POWER_BANK, SUIT, TOOL, TOY);
  }

  private Category() {
    // nothing
  }
}
