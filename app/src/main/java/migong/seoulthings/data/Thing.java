package migong.seoulthings.data;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class Thing {

  @NonNull
  @SerializedName("id")
  private final UUID mId;

  @NonNull
  @SerializedName("category")
  private final String mCategory;

  @NonNull
  @SerializedName("contents")
  private final String mContents;

  @NonNull
  @SerializedName("locations")
  private final Location mLocation;

  public Thing(@NonNull UUID id, @NonNull String category, @NonNull String contents,
      @NonNull Location location) {
    this.mId = id;
    this.mCategory = category;
    this.mContents = contents;
    this.mLocation = location;
  }

  @NonNull
  public String getId() {
    return mId.toString();
  }

  @NonNull
  public String getCategory() {
    return mCategory;
  }

  @NonNull
  public String getContents() {
    return mContents;
  }

  @NonNull
  public Location getLocation() {
    return mLocation;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Thing{");
    sb.append("id=").append(mId);
    sb.append(", category='").append(mCategory).append('\'');
    sb.append(", contents='").append(mContents).append('\'');
    sb.append(", location=").append(mLocation);
    sb.append('}');
    return sb.toString();
  }
}
