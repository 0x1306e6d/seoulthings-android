package migong.seoulthings.ui.main.profile.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import migong.seoulthings.ui.main.profile.ProfileView;
import migong.seoulthings.ui.main.profile.comments.MyCommentsFragment;
import migong.seoulthings.ui.main.profile.donations.MyDonationsFragment;
import migong.seoulthings.ui.main.profile.things.MyThingsFragment;

public class ProfilePagerAdapter extends FragmentPagerAdapter {

  public ProfilePagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case ProfileView.MY_THINGS_POSITION:
        return new MyThingsFragment();
      case ProfileView.MY_DONATIONS_POSITION:
        return new MyDonationsFragment();
      case ProfileView.MY_COMMENTS_POSITION:
        return new MyCommentsFragment();
    }
    return null;
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case ProfileView.MY_THINGS_POSITION:
        return "대여 정보";
      case ProfileView.MY_DONATIONS_POSITION:
        return "내 글보기";
      case ProfileView.MY_COMMENTS_POSITION:
        return "내 댓글보기";
    }
    return super.getPageTitle(position);
  }
}
