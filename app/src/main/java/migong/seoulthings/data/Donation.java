package migong.seoulthings.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import java.util.Arrays;

public class Donation {

  @Exclude
  private String mFirebaseId;
  private String mTitle;
  private String mContents;
  private String mDong;
  private double mLatitude;
  private double mLongitude;
  private String[] mImageUrls;
  private Timestamp mCreatedAt;
  private Timestamp mUpdatedAt;

  public Donation() {
    // For FireStore deserialization.
  }

  public String getFirebaseId() {
    return mFirebaseId;
  }

  public void setFirebaseId(String firebaseId) {
    mFirebaseId = firebaseId;
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

  public String getDong() {
    return mDong;
  }

  public void setDong(String dong) {
    mDong = dong;
  }

  public double getLatitude() {
    return mLatitude;
  }

  public void setLatitude(double latitude) {
    mLatitude = latitude;
  }

  public double getLongitude() {
    return mLongitude;
  }

  public void setLongitude(double longitude) {
    mLongitude = longitude;
  }

  public Timestamp getCreatedAt() {
    return mCreatedAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    mCreatedAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return mUpdatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    mUpdatedAt = updatedAt;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Donation{");
    sb.append("title='").append(mTitle).append('\'');
    sb.append(", contents='").append(mContents).append('\'');
    sb.append(", dong='").append(mDong).append('\'');
    sb.append(", latitude=").append(mLatitude);
    sb.append(", longitude=").append(mLongitude);
    sb.append(", imageUrls=").append(Arrays.toString(mImageUrls));
    sb.append(", createdAt=").append(mCreatedAt);
    sb.append(", updatedAt=").append(mUpdatedAt);
    sb.append('}');
    return sb.toString();
  }
}
