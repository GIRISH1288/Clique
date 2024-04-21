package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProfilePortfolioFragment extends Fragment {
    private FloatingActionButton btnaddportfolio1;
    private RecyclerView portfoliorecyler;
    private List<portfoliostr> projectlist;
    private FirebaseFirestore db;
    private portfolioAdapter portfolioAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_portfolio, container, false);
        db = FirebaseFirestore.getInstance();
        projectlist = new ArrayList<>();
        btnaddportfolio1 = view.findViewById(R.id.btnaddportfolio1);
        portfoliorecyler = view.findViewById(R.id.portfoliorecyler);
        portfoliorecyler.setHasFixedSize(true);
        portfoliorecyler.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        db.collection("users").document(userID).collection("projectdetails")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String projectName = document.getString("projectName");
                            String projectDescription = document.getString("projectDescription");
                            String projectImage = document.getString("projectImage");
                            // Retrieve the timestamp field from the document
                            Long timestampLong = document.getLong("timestamp"); // Assuming timestamp field name is "timestamp"
                            long timestamp = timestampLong != null ? timestampLong : 0; // Null check and fallback value
                            portfoliostr project = new portfoliostr(projectName, projectDescription, projectImage, timestamp);
                            projectlist.add(project);
                        }
                        // Sort the list with newest items first based on timestamp
                        Collections.sort(projectlist, new Comparator<portfoliostr>() {
                            @Override
                            public int compare(portfoliostr o1, portfoliostr o2) {
                                // Compare the timestamps of o1 and o2
                                return Long.compare(o2.getTimestamp(), o1.getTimestamp());
                            }
                        });

                        // Initialize the adapter after sorting the list
                        portfolioAdapter = new portfolioAdapter(projectlist);
                        portfoliorecyler.setAdapter(portfolioAdapter);
                        // Notify adapter after adding all items
                        portfolioAdapter.notifyDataSetChanged();
                    }
                });


        // Button to add a new portfolio item
        btnaddportfolio1.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), portfolioAdd_activity.class);
            startActivity(intent);
        });

        return view;
    }
}

