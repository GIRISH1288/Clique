package com.project.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    androidx.appcompat.widget.SearchView searchView;
    ImageView search_user;
    EditText etSearchUser;
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
        etSearchUser = findViewById(R.id.etSearchUser);
        search_user = findViewById(R.id.search_user);
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
        search_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.clear();
                retrieveUserData();
            }
        });


    }
    private void retrieveUserData() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userList.clear(); // Clear previous results
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve user data from Firestore document
                            String username = document.getString("username");
                            if (username != null && username.toLowerCase().contains(etSearchUser.getText().toString().toLowerCase())) {
                                String fullName = document.getString("name");
                                String userID = document.getId();
                                String profileImageUrl = document.getString("profileImageUrl");
                                String notificationToken = document.getString("notification_token");
                                User user = new User(username, fullName, profileImageUrl, notificationToken, userID);
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