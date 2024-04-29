package com.project.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    private RecyclerView discoverPeople;
    private UserAdapter userAdapter;
    private List<User> userList;
    private FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Initialize RecyclerView
        discoverPeople = view.findViewById(R.id.discoverPeople);
        discoverPeople.setHasFixedSize(true);
        discoverPeople.setLayoutManager(new LinearLayoutManager(requireContext()));
        // Initialize user list
        userList = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve user data from Firestore document
                            String username = document.getString("username");
                            String fullName = document.getString("name");
                            String userID = document.getId();
                            String profileImageUrl = document.getString("profileImageUrl");
                            String notificationToken = document.getString("notification_token");
                            User user = new User(username, fullName, profileImageUrl, notificationToken, userID);
                            if (userID.equals(mAuth.getCurrentUser().getUid())) {} else {
                                userList.add(user);
                            }
                        }

                        // Initialize and set adapter
                        userAdapter = new UserAdapter(userList);
                        discoverPeople.setAdapter(userAdapter);
                    } else {
                        // Handle errors
                        Toast.makeText(requireContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}