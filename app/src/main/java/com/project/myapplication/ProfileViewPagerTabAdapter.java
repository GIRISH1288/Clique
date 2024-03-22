package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfileViewPagerTabAdapter extends FragmentPagerAdapter {
    public ProfileViewPagerTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            return new ProfileAboutFragment();
        } else if (position == 1) {
            return new ProfilePostsFragment();
        } else {
            return new ProfilePortfolioFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) {
            return "About";
        } else if (position == 1) {
            return "Posts";
        } else {
            return "Portfolio";
        }
    }
}
