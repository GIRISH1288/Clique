package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends Fragment {
    private FloatingActionButton btnaddcommunity;
    private RecyclerView Communityrecycler;

    private List<Communitystr> Communitylist;
    private FirebaseFirestore db;
   private  CommunityAdapter communityAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_community, container, false);
        db = FirebaseFirestore.getInstance();
        Communitylist=new ArrayList<>();
        btnaddcommunity = view.findViewById(R.id.btnaddcommunity);
        Communityrecycler = view.findViewById(R.id.Communityrecycler);
        Communityrecycler.setHasFixedSize(true);
        Communityrecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseAuth mAUth = FirebaseAuth.getInstance();
        String CommunityID = mAUth.getCurrentUser().getUid();

        db.collection("communities")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult(); // Get the query snapshot
                        if (querySnapshot != null && !querySnapshot.isEmpty()) { // Check if the snapshot is not empty
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                String communityID = document.getId(); // Retrieve community ID
                                String communityName = document.getString("Communityname");
                                String communityDescription = document.getString("Communitydetail");
                                String communityImage = document.getString("communityImage");
                                String communityCreatorinfo = document.getString("Creatorinfo");

                                Communitystr community = new Communitystr(communityID, communityName, communityDescription, communityCreatorinfo, communityImage);
                                Communitylist.add(community);
                            }

                            // Initialize the adapter
                            communityAdapter = new CommunityAdapter(Communitylist);
                            Communityrecycler.setAdapter(communityAdapter);
                            // Notify adapter after adding all items
                            communityAdapter.notifyDataSetChanged();
                        } else {
                            // No documents found in the collection
                        }
                    }
                });






        btnaddcommunity.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // Intent intent= new Intent()
        Intent intent = new Intent(getActivity(),CommunityAddActivity.class);
        startActivity(intent);

    }
});


        return view;
    }
}