package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class MainUI extends AppCompatActivity {
    TabLayout mainUITab;
    ViewPager mainUIViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        mainUITab = findViewById(R.id.mainUITab);
        mainUIViewPager = findViewById(R.id.mainUIViewPager);
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getSupportFragmentManager());
        mainUIViewPager.setAdapter(adapter);
        mainUITab.setupWithViewPager(mainUIViewPager);


    }
}