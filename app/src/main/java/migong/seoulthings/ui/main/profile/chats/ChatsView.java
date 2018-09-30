package migong.seoulthings.ui.main.profile.chats;

import com.google.firebase.firestore.DocumentSnapshot;

public interface ChatsView {

  void clearSnapshots();

  void addSnapshot(int index, DocumentSnapshot snapshot);

  void modifySnapshot(int oldIndex, int newIndex, DocumentSnapshot snapshot);

  void removeSnapshot(int index);
}
