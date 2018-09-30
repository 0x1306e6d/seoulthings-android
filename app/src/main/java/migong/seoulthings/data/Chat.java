package migong.seoulthings.data;

import com.google.firebase.Timestamp;
import java.util.List;

public class Chat {

  private String mFirebaseId;
  private String mLastMessage;
  private List<String> mChatters;
  private Timestamp mCreateAt;
  private Timestamp mUpdatedAt;

  public String getFirebaseId() {
    return mFirebaseId;
  }

  public void setFirebaseId(String firebaseId) {
    mFirebaseId = firebaseId;
  }

  public List<String> getChatters() {
    return mChatters;
  }

  public void setChatters(List<String> chatters) {
    mChatters = chatters;
  }

  public String getLastMessage() {
    return mLastMessage;
  }

  public void setLastMessage(String lastMessage) {
    mLastMessage = lastMessage;
  }

  public Timestamp getCreateAt() {
    return mCreateAt;
  }

  public void setCreateAt(Timestamp createAt) {
    mCreateAt = createAt;
  }

  public Timestamp getUpdatedAt() {
    return mUpdatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    mUpdatedAt = updatedAt;
  }
}
