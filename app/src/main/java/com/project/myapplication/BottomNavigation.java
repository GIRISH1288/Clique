package com.project.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.myapplication.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class BottomNavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_navigation);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        loadFrag(new HomeFragment());


        FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(BottomNavigation.this, "failed token retrive", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String token = task.getResult();
                                DocumentReference documentReference = db.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("notification_token", token);

                                documentReference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(BottomNavigation.this, "Token Updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(BottomNavigation.this, "Failed to update token", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                try {
                    int id = item.getItemId();
                    if (id == R.id.nav_home) {
                        loadFrag(new HomeFragment());
                    } else if (id == R.id.nav_add) {
                        loadFrag(new CreatePostFragment());
                    } else if (id == R.id.nav_notification) {
                        loadFrag(new NotificationFragment());
                    } else if (false/*id == R.id.nav_message*/) {
                        loadFrag(new ChatFragment());
                    } else {
                        loadFrag(new ProfileFragment());
                    }
                } catch (Exception e) {

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