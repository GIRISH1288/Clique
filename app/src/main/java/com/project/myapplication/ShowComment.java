package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShowComment extends AppCompatActivity {
    RecyclerView comments;
    FrameLayout chatActivitySend;
    EditText chatActivityInputMessage;
    String postID;
    String postUserUserID;
    CommentAdapter commentAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;
    private String username;
    private String currestUserProfilePicture;
    List<Comment> CommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);
        Intent i = getIntent();
        postUserUserID = i.getStringExtra("postUserUserID");
        postID = i.getStringExtra("postID");
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db.collection("users").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            username = documentSnapshot.getString("username");
                            currestUserProfilePicture = documentSnapshot.getString("profileImageUrl");
                        }
                    }
                });

        comments = findViewById(R.id.comments);
        comments.setHasFixedSize(true);
        comments.setLayoutManager(new LinearLayoutManager(ShowComment.this));
        CommentList = new ArrayList<>();


        db.collection("users").document(postUserUserID)
                .collection("posts")
                .document(postID)
                .collection("Comments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String CommentedUserID = documentSnapshot.getString("userID");
                            String CommentUserUserName = documentSnapshot.getString("userName");
                            String profileImageUrl = documentSnapshot.getString("dp");
                            String CommentCreated = documentSnapshot.getString("comment");
                            Comment comm = new Comment(profileImageUrl, CommentUserUserName, CommentCreated, CommentedUserID);
                            CommentList.add(comm);

                            Toast.makeText(ShowComment.this, postUserUserID, Toast.LENGTH_LONG).show();
                            Toast.makeText(ShowComment.this, postID, Toast.LENGTH_LONG).show();

                        }
                        commentAdapter = new CommentAdapter(CommentList);
                        comments.setAdapter(commentAdapter);
                        commentAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(ShowComment.this, "Failed to reload likes", Toast.LENGTH_LONG).show();
                    }
                });

        chatActivitySend = findViewById(R.id.chatActivitySend);
        chatActivityInputMessage = findViewById(R.id.chatActivityInputMessage);

        chatActivitySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(chatActivityInputMessage.getText().toString())) {
                    Toast.makeText(ShowComment.this, "Enter A Comment", Toast.LENGTH_SHORT).show();
                } else {
                    String commentText = chatActivityInputMessage.getText().toString();
                    Map<String, Object> CommentData = new HashMap<>();
                    CommentData.put("userID", userID);
                    CommentData.put("userName", username);
                    CommentData.put("comment", commentText);
                    CommentData.put("dp", currestUserProfilePicture);
                    db.collection("users")
                            .document(postUserUserID)
                            .collection("posts")
                            .document(postID)
                            .collection("Comments")
                            .add(CommentData)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(ShowComment.this, "Comment Added", Toast.LENGTH_LONG).show();
                                    Toast.makeText(ShowComment.this, postUserUserID, Toast.LENGTH_LONG).show();
                                    Toast.makeText(ShowComment.this, postID, Toast.LENGTH_LONG).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        });




    }
}