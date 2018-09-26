package migong.seoulthings.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import migong.seoulthings.R;
import migong.seoulthings.ui.profile.adapter.ProfilePagerAdapter;

public class ProfileFragment extends Fragment implements ProfileView {

  private TextView mTitleText;
  private ImageView mPhotoImage;
  private TextView mEmailText;

  private ViewPager mPager;
  private TabLayout mTabLayout;
  private ProfilePagerAdapter mPagerAdapter;

  private ProfilePresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.profile_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupAppBar(view);
    setupProfile(view);
    setupTab(view);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new ProfilePresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override
  public void setTitle(String title) {
    mTitleText.setText(title);
  }

  @Override
  public void setPhoto(Uri uri) {
    Log.d("ProfileFragment", "setPhoto() called with: uri = [" + uri + "]");
    Picasso.get()
        .load(uri)
        .placeholder(R.drawable.ic_person_black_48)
        .into(mPhotoImage);
  }

  @Override
  public void setEmail(String email) {
    mEmailText.setText(email);
  }

  private void setupAppBar(@NonNull View view) {
    mTitleText = view.findViewById(R.id.profile_title);

    ImageButton messageButton = view.findViewById(R.id.profile_message_button);
    messageButton.setOnClickListener(v -> mPresenter.onMessageButtonClicked());
  }

  private void setupProfile(@NonNull View view) {
    mPhotoImage = view.findViewById(R.id.profile_photo);
    mEmailText = view.findViewById(R.id.profile_email);

    Button modifyButton = view.findViewById(R.id.profile_modify_button);
    modifyButton.setOnClickListener(v -> mPresenter.onModifyButtonClicked());
  }

  private void setupTab(@NonNull View view) {
    mPager = view.findViewById(R.id.profile_viewpager);
    mTabLayout = view.findViewById(R.id.profile_tablayout);
    mPagerAdapter = new ProfilePagerAdapter(getChildFragmentManager());
    mPager.setAdapter(mPagerAdapter);
    mTabLayout.setupWithViewPager(mPager);
  }
}
