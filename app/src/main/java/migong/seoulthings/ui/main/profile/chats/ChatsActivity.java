package migong.seoulthings.ui.main.profile.chats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import io.reactivex.disposables.CompositeDisposable;
import migong.seoulthings.R;
import migong.seoulthings.ui.main.profile.chats.adapter.ChatRecyclerAdapter;

public class ChatsActivity extends AppCompatActivity implements ChatsView {

  private RecyclerView mChatRecyclerView;
  private ChatRecyclerAdapter mChatRecyclerAdapter;

  private ChatsPresenter mPresenter;
  private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chats_activity);

    setupAppBar();
    setupChatRecycler();

//    KxZU12E6XjNlBa3P8DXrN7qISxl2
//    hjZ3NOaTiEUu7xP4g88xAXftUXI3
    mPresenter = new ChatsPresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override
  public void clearSnapshots() {
    mChatRecyclerAdapter.clear();
  }

  @Override
  public void addSnapshot(int index, DocumentSnapshot snapshot) {
    mChatRecyclerAdapter.add(index, snapshot);
  }

  @Override
  public void modifySnapshot(int oldIndex, int newIndex, DocumentSnapshot snapshot) {
    mChatRecyclerAdapter.modify(oldIndex, newIndex, snapshot);
  }

  @Override
  public void removeSnapshot(int index) {
    mChatRecyclerAdapter.remove(index);
  }

  private void setupAppBar() {
    ImageButton backButton = findViewById(R.id.chats_back_button);
    backButton.setOnClickListener(v -> onBackPressed());
  }

  private void setupChatRecycler() {
    mChatRecyclerView = findViewById(R.id.chats_recycler);
    mChatRecyclerView.setHasFixedSize(true);
    mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mChatRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    mChatRecyclerAdapter = new ChatRecyclerAdapter(mCompositeDisposable,
        chatId -> mPresenter.onChatClicked(chatId));
    mChatRecyclerView.setAdapter(mChatRecyclerAdapter);
  }
}
