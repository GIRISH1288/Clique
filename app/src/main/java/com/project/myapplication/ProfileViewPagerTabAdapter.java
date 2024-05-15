package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfileViewPagerTabAdapter extends FragmentPagerAdapter {
    String userID;
    boolean showAddButton;
    public ProfileViewPagerTabAdapter(@NonNull FragmentManager fm, String userID, boolean showAddButton) {
        super(fm);
        this.userID = userID;
        this.showAddButton = showAddButton;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            return new ProfileAboutFragment(userID);
        } else if (position == 1) {
            return new ProfilePostsFragment(userID);
        } else {
            return new ProfilePortfolioFragment(userID, showAddButton);
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
