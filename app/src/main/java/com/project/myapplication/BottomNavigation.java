package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.myapplication.databinding.ActivityMainBinding;

public class BottomNavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_navigation);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        loadFrag(new HomeFragment());
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    loadFrag(new HomeFragment());
                } else if (id == R.id.nav_add) {
                    loadFrag(new CreatePostFragment());
                } else if (id == R.id.nav_notification) {
                    loadFrag(new NotificationFragment());
                } else if (id == R.id.nav_message) {
                    loadFrag(new ChatFragment());
                } else {
                    loadFrag(new ProfileFragment());
                }
                return true;
            }
        });


    }
    public void loadFrag(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.bottomNavigationFrameLayout,fragment);

        ft.commit();
    }
}