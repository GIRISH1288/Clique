package com.project.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ProfileFragment extends Fragment {
    private TabLayout profileTab;
    private ViewPager profileViewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.profileToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("");
        profileTab = view.findViewById(R.id.profileTab);
        profileViewPager = view.findViewById(R.id.profileViewPager);
        ProfileViewPagerTabAdapter adapter = new ProfileViewPagerTabAdapter(getChildFragmentManager());
        profileViewPager.setAdapter(adapter);
        profileTab.setupWithViewPager(profileViewPager);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_share) {
            // Handle share action
            Toast.makeText(requireContext(), "Share clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.profile_settings) {
            // Handle settings action
            Toast.makeText(requireContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
