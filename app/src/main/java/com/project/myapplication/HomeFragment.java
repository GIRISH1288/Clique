package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    private TabLayout mainUITab;
    private ViewPager mainUIViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.title);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("");

        mainUITab = rootView.findViewById(R.id.mainUITab);
        mainUIViewPager = rootView.findViewById(R.id.mainUIViewPager);

        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getChildFragmentManager());
        mainUIViewPager.setAdapter(adapter);
        mainUITab.setupWithViewPager(mainUIViewPager);

        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_profiles) {
            startActivity(new Intent(requireContext(), SearchActivity.class));
            return true;
        } else if (item.getItemId() == R.id.profile_settings) {
            // Handle settings action
            Intent intent = new Intent(requireContext(), SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}