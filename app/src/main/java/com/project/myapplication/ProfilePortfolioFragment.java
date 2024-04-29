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
    boolean showAddButton;
    String userID;
    public ProfilePortfolioFragment(String userID, boolean showAddButton) {
        this.userID = userID;
        this.showAddButton = showAddButton;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_portfolio, container, false);
        db = FirebaseFirestore.getInstance();
        projectlist = new ArrayList<>();
        btnaddportfolio1 = view.findViewById(R.id.btnaddportfolio1);
        if (!showAddButton) {
            btnaddportfolio1.setVisibility(View.GONE);
        }
        portfoliorecyler = view.findViewById(R.id.portfoliorecyler);
        portfoliorecyler.setHasFixedSize(true);
        portfoliorecyler.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        db.collection("users").document(userID).collection("projectdetails")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String projectId = document.getId(); // Retrieve projectId
                            String projectName = document.getString("projectName");
                            String projectDescription = document.getString("projectDescription");
                            String projectImage = document.getString("projectImage");
                            String projectlink = document.getString("projectlink");
                            portfoliostr project = new portfoliostr(projectId, projectName, projectDescription, projectImage ,projectlink); // Pass projectId to the constructor
                            projectlist.add(project);
                        }
                        // Initialize the adapter
                        portfolioAdapter = new portfolioAdapter(projectlist);
                        portfoliorecyler.setAdapter(portfolioAdapter);
                        // Notify adapter after adding all items
                        portfolioAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
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


