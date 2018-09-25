package migong.seoulthings.data;

import org.joda.time.DateTime;

public class Donation {

  private String mTitle;
  private String mContents;
  private String mLocation;
  private DateTime mCreatedAt;
  private DateTime mUpdatedAt;

  public Donation() {
    // For FireStore deserialization.
  }

  public Donation(String title, String contents, String location, DateTime createdAt,
      DateTime updatedAt) {
    this.mTitle = title;
    this.mContents = contents;
    this.mLocation = location;
    this.mCreatedAt = createdAt;
    this.mUpdatedAt = updatedAt;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getContents() {
    return mContents;
  }

  public void setContents(String contents) {
    mContents = contents;
  }

  public String getLocation() {
    return mLocation;
  }

  public void setLocation(String location) {
    mLocation = location;
  }

  public DateTime getCreatedAt() {
    return mCreatedAt;
  }

  public DateTime getUpdatedAt() {
    return mUpdatedAt;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Donation{");
    sb.append("title='").append(mTitle).append('\'');
    sb.append(", contents='").append(mContents).append('\'');
    sb.append(", location='").append(mLocation).append('\'');
    sb.append(", createdAt=").append(mCreatedAt);
    sb.append(", updatedAt=").append(mUpdatedAt);
    sb.append('}');
    return sb.toString();
  }
}
