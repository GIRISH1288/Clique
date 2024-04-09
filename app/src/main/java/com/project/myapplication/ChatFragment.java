package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerViewMessages;
    private MessageUserAdapter messageUserAdapter;
    private List<MessageUser> messageUserList;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        recyclerViewMessages = rootView.findViewById(R.id.recyclerViewMessages);
        FloatingActionButton fab = rootView.findViewById(R.id.btnFloatingActionButtonMessages);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
        messageUserList = new ArrayList<>();
        messageUserList.clear();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a dialog or activity to display all users
                startActivity(new Intent(requireContext(), ShowUserForChat.class));
            }
        });

        db.collection("users")
                .document(userID)
                .collection("chats")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AtomicInteger count = new AtomicInteger();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String chatUserId = documentSnapshot.getString("userId");

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
                                            messageUserAdapter = new MessageUserAdapter(messageUserList, requireContext());
                                            recyclerViewMessages.setAdapter(messageUserAdapter);
                                            messageUserAdapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    }  // Handle task failure

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "NO chats present", Toast.LENGTH_SHORT).show();
                    }
                });



        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Clear the existing messageUserList
        messageUserList.clear();
        // Reload data from Firestore
        loadChatUsers();
    }

    private void loadChatUsers() {
        messageUserList.clear();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("users")
                .document(userID)
                .collection("chats")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AtomicInteger count = new AtomicInteger();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String chatUserId = documentSnapshot.getString("userId");

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
                                            messageUserAdapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    } else {
                        // Handle task failure
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "NO chats present", Toast.LENGTH_SHORT).show());
    }

}
