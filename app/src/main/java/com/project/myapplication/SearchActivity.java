package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    androidx.appcompat.widget.SearchView searchView;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private FirebaseFirestore db;
    String loggedInUsername;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCityPeople);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize user list
        userList = new ArrayList<>();
        DocumentReference documentReference = db.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    loggedInUsername = documentSnapshot.getString("username");
                } else {
                    // Document does not exist
                    Toast.makeText(SearchActivity.this, "error getting username", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(SearchActivity.this, "Error loading Page", Toast.LENGTH_LONG).show();
            }
        });


        // Retrieve user data from Firestore
        retrieveUserData();
    }
    private void retrieveUserData() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve user data from Firestore document
                            String username = document.getString("username");
                            String fullName = document.getString("name");
                            String profileImageUrl = document.getString("profileImageUrl");
                            User user = new User(username, fullName, profileImageUrl);
                            if (Objects.equals(username, loggedInUsername)) {} else {
                                userList.add(user);
                            }
                        }

                        // Initialize and set adapter
                        userAdapter = new UserAdapter(userList);
                        recyclerView.setAdapter(userAdapter);
                    } else {
                        // Handle errors
                        Toast.makeText(SearchActivity.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}