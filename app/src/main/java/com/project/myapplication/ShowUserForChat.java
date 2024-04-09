package com.project.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowUserForChat extends AppCompatActivity implements CreateChatUserAdapter.OnItemClickListener {
    private RecyclerView recyclerViewMessages;
    private CreateChatUserAdapter messageUserAdapter;
    private List<MessageUser> messageUserList;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_for_chat);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        recyclerViewMessages = findViewById(R.id.recyclerViewCreateChatUsers);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        messageUserList = new ArrayList<>();
        db.collection("users")
                .document(userID)
                .collection("connection")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AtomicInteger count = new AtomicInteger();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String chatUserId = documentSnapshot.getString("userID");

                            db.collection("users")
                                    .document(chatUserId)
                                    .get()
                                    .addOnSuccessListener(document -> {
                                        String fullName = document.getString("name");
                                        String profileImageUrl = document.getString("profileImageUrl");
                                        String notificationToken = document.getString("notification_token");

                                        MessageUser messageUser = new MessageUser(fullName, profileImageUrl, notificationToken, chatUserId);
                                        messageUserList.add(messageUser);
                                        count.getAndIncrement();

                                        if (count.get() == task.getResult().size()) {
                                            messageUserAdapter = new CreateChatUserAdapter(messageUserList, this);
                                            recyclerViewMessages.setAdapter(messageUserAdapter);
                                        }
                                    });
                        }
                    } else {
                        // Handle task failure
                    }
                });

    }
    @Override
    public void onItemClick(String userId) {
        // Handle item click, for example, add chatUserId to the chatuser collection
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(this, "hekko", Toast.LENGTH_SHORT).show();
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        db.collection("users")
                .document(currentUserID)
                .collection("chats")
                .whereEqualTo("userID", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                Toast.makeText(ShowUserForChat.this, "Accept pending request",Toast.LENGTH_LONG).show();
                            } else {
                                // If no pending request, add the user to the chat
                                FirebaseFirestore.getInstance()
                                        .collection("users")
                                        .document(currentUserID)  // Assuming you're adding under current user's document
                                        .collection("chats")
                                        .document()
                                        .set(userData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Document added successfully
                                                // You can show a toast or perform any other action
                                                Toast.makeText(ShowUserForChat.this, "Chat user added successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle any errors
                                                Toast.makeText(ShowUserForChat.this, "Failed to add chat user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            // Handle errors
                            Toast.makeText(ShowUserForChat.this, "Failed to check pending requests: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}