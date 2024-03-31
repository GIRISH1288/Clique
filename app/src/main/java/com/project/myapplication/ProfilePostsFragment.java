package com.project.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProfilePostsFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private String username;
    private String profileImageUrl;
    private List<Posts> postList;
    FirebaseFirestore db;
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_posts, container, false);
        db = FirebaseFirestore.getInstance();
        postList = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        recyclerView = rootView.findViewById(R.id.postsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        db.collection("users").document(userID).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    username = documentSnapshot.getString("username");
                                    profileImageUrl = documentSnapshot.getString("profileImageUrl");
                                }
                            }
                        });
        db.collection("users")
                .document(userID)
                .collection("posts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String caption = document.getString("caption");
                            String postImageUrl = document.getString("imageUrl");
                            Posts post = new Posts(profileImageUrl, username, postImageUrl, caption);
                            postList.add(post);
                        }
                    }
                    postAdapter = new PostAdapter(postList);
                    recyclerView.setAdapter(postAdapter);
                });
        return rootView;
    }
}