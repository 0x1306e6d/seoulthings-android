package migong.seoulthings.ui.community.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import migong.seoulthings.ui.donations.DonationsFragment;
import migong.seoulthings.ui.reviews.ReviewsFragment;

public class CommunityPagerAdapter extends FragmentPagerAdapter {

  public CommunityPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new DonationsFragment();
      case 1:
        return new ReviewsFragment();
      default:
        return null;
    }
  }

  @Override
  public int getCount() {
    return 2;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "물품나눔";
      case 1:
        return "대여후기";
    }
    return super.getPageTitle(position);
  }
}
