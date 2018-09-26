package migong.seoulthings.data;

import android.content.Context;
import android.support.annotation.NonNull;
import migong.seoulthings.R;
import org.apache.commons.lang3.StringUtils;

public final class Category {

  public static final String ALL = "ALL";
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
    return StringUtils.equalsAny(category,
        ALL, BICYCLE, MEDICAL_DEVICE, POWER_BANK, SUIT, TOOL, TOY);
  }

  public static String valueOf(@NonNull Context context, String category) {
    switch (category) {
      case BICYCLE:
        return context.getString(R.string.bicycle);
      case MEDICAL_DEVICE:
        return context.getString(R.string.medical_device);
      case POWER_BANK:
        return context.getString(R.string.power_bank);
      case SUIT:
        return context.getString(R.string.suit);
      case TOOL:
        return context.getString(R.string.tool);
      case TOY:
        return context.getString(R.string.toy);
    }
    return StringUtils.EMPTY;
  }

  private Category() {
    // nothing
  }
}
