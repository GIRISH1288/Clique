package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerTabAdapter extends FragmentPagerAdapter {
    public ViewPagerTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            return new PeopleFragment();
        } else if (position == 1) {
            return new CommunityFragment();
        } else {
            return new DiscoverFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) {
            return "People";
        } else if (position == 1) {
            return "Community";
        } else {
            return "Discover";
        }
    }
}
