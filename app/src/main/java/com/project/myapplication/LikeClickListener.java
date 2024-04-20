package com.project.myapplication;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LikeClickListener implements PostAdapter.OnLikeClickListener {
    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;
    private String username;

    public LikeClickListener(Context context) {
        mContext = context;
    }

    @Override
    public void onLikeClick(int position, List<Posts> postList) {
        // Use mContext to create Toast
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db.collection("users").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            username = documentSnapshot.getString("username");
                        }
                    }
                });

        DocumentReference userRef = db.collection("users").document(postList.get(position).getPostUserUserID());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {


                    // Add the like to the Firestore
                    Map<String, Object> likeData = new HashMap<>();
                    likeData.put("userID", userID);
                    likeData.put("userName", username);
                    likeData.put("timestamp", FieldValue.serverTimestamp());

                    db.collection("users")
                            .document(postList.get(position).getPostUserUserID())
                            .collection("posts")
                            .document(postList.get(position).getPostID())
                            .collection("likes")
                            .add(likeData)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(mContext, "Liked!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mContext, "Failed to like!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
}
