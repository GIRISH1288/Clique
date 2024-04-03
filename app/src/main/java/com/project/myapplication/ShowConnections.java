package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowConnections extends AppCompatActivity {
    private RecyclerView recyclerViewShowConnections;
    private ConnectedUserAdapter connectedUserAdapter;
    private List<ConnectedUser> connectedUsers;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_connections);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        recyclerViewShowConnections = findViewById(R.id.recyclerViewShowConnections);
        recyclerViewShowConnections.setHasFixedSize(true);
        recyclerViewShowConnections.setLayoutManager(new LinearLayoutManager(ShowConnections.this));
        connectedUsers = new ArrayList<>();
        db.collection("users").document(userID).collection("connection")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String connectedUserID = documentSnapshot.getString("userID");
                            assert connectedUserID != null;
                            db.collection("users")
                                    .document(connectedUserID)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String fullName = documentSnapshot.getString("name");
                                            String profileImageUrl = documentSnapshot.getString("profileImageUrl");
                                            ConnectedUser connectedUser = new ConnectedUser(fullName, profileImageUrl, connectedUserID);
                                            connectedUsers.add(connectedUser);

                                            // Notify adapter of data change after adding each item
                                            connectedUserAdapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                        // Initialize and set adapter after adding all items
                        connectedUserAdapter = new ConnectedUserAdapter(connectedUsers);
                        recyclerViewShowConnections.setAdapter(connectedUserAdapter);
                    } else {
                        Toast.makeText(ShowConnections.this, "Failed to reload request", Toast.LENGTH_LONG).show();
                    }
                });

    }
}