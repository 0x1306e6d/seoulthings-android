package migong.seoulthings.data;

import com.google.firebase.Timestamp;

public class Review {

  private String mAuthorId;
  private String mContents;
  private Timestamp mUpdatedAt;

  public String getAuthorId() {
    return mAuthorId;
  }

  public void setAuthorId(String authorId) {
    mAuthorId = authorId;
  }

  public String getContents() {
    return mContents;
  }

  public void setContents(String contents) {
    mContents = contents;
  }

  public Timestamp getUpdatedAt() {
    return mUpdatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    mUpdatedAt = updatedAt;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Review{");
    sb.append("authorId='").append(mAuthorId).append('\'');
    sb.append(", contents='").append(mContents).append('\'');
    sb.append(", updatedAt='").append(mUpdatedAt).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
