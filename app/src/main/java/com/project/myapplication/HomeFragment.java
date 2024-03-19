package com.project.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    private TabLayout mainUITab;
    private ViewPager mainUIViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mainUITab = rootView.findViewById(R.id.mainUITab);
        mainUIViewPager = rootView.findViewById(R.id.mainUIViewPager);

        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getChildFragmentManager());
        mainUIViewPager.setAdapter(adapter);
        mainUITab.setupWithViewPager(mainUIViewPager);

        return rootView;
    }
}