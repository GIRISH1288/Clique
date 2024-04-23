package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowLikes extends AppCompatActivity {
    private RecyclerView userLikes;
    private ConnectedUserAdapter likedUserAdapter;
    private List<ConnectedUser> likedUsers;
    private FirebaseFirestore db;
    String postUserUserID;
    String postID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_likes);

        Intent i = getIntent();
        postUserUserID = i.getStringExtra("postUserUserID");
        postID = i.getStringExtra("postID");
        db = FirebaseFirestore.getInstance();
        userLikes = findViewById(R.id.userLikes);
        userLikes.setHasFixedSize(true);
        userLikes.setLayoutManager(new LinearLayoutManager(ShowLikes.this));
        likedUsers = new ArrayList<>();
        db.collection("users").document(postUserUserID)
                .collection("posts")
                .document(postID)
                .collection("likes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String likedUserID = documentSnapshot.getString("userID");
                            assert likedUserID != null;
                            db.collection("users")
                                    .document(likedUserID)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String fullName = documentSnapshot.getString("name");
                                            String profileImageUrl = documentSnapshot.getString("profileImageUrl");
                                            ConnectedUser connectedUser = new ConnectedUser(fullName, profileImageUrl, likedUserID);
                                            likedUsers.add(connectedUser);

                                            // Notify adapter of data change after adding each item
                                            likedUserAdapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                        // Initialize and set adapter after adding all items
                        likedUserAdapter = new ConnectedUserAdapter(likedUsers);
                        userLikes.setAdapter(likedUserAdapter);
                    } else {
                        Toast.makeText(ShowLikes.this, "Failed to reload likes", Toast.LENGTH_LONG).show();
                    }
                });
    }
}