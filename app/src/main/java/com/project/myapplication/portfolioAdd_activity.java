package com.project.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.HashMap;
import java.util.Map;

public class portfolioAdd_activity extends AppCompatActivity {
    EditText etportfolioname, etportfoliodescription ,etportfoliolink;
    ImageView ivprojectpicture;
    Button btnaddproject;
    View addportfolioanim;
    LottieAnimationView loading_animation;
    Uri uri;
    String userId, projectname, projectdescription , projectlink;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_add);
        addportfolioanim = findViewById(R.id.addportfolioanim);
        ivprojectpicture = findViewById(R.id.ivprojectpicture);
        btnaddproject = findViewById(R.id.btnaddproject);
        etportfolioname = findViewById(R.id.etportfolioname);
        etportfoliolink=findViewById(R.id.etportfoliolink);
        loading_animation = findViewById(R.id.animLoading);
        etportfoliodescription = findViewById(R.id.etportfoliodescription);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance(); // Initialize mAuth here
        userId = mAuth.getCurrentUser().getUid();

        addportfolioanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivprojectpicture.setVisibility(View.VISIBLE);
                ImagePicker.Companion.with(portfolioAdd_activity.this)
                        .galleryOnly()
                        .crop(3f, 4f)
                        .compress(1024)
                        .maxResultSize(1080, 1440)
                        .start(10);
            }
        });

        btnaddproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get project name and description
                projectname = etportfolioname.getText().toString().trim();
                projectdescription = etportfoliodescription.getText().toString().trim();
                projectlink = etportfoliolink.getText().toString().trim();

                // Check if project name, description, and image are empty
                if (TextUtils.isEmpty(projectname) || TextUtils.isEmpty(projectdescription) ||TextUtils.isEmpty(projectlink)|| uri == null) {
                    Toast.makeText(portfolioAdd_activity.this, "Please enter details", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are filled, start uploading to Firebase
                    uploadprojectToFirebase();

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();
            Glide.with(this)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(ivprojectpicture);
            ivprojectpicture.setVisibility(View.VISIBLE);
        }
    }

    private void uploadprojectToFirebase() {
        // Get current user ID
        loading_animation.setVisibility(View.VISIBLE);
        String userId = mAuth.getCurrentUser().getUid();

        // Reference to Firestore and Firebase Storage
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Reference to the user's document
        DocumentReference userRef = db.collection("users").document(userId);

        // Create a reference to the storage location
        StorageReference imageRef = storageRef.child("projectImages").child(userId + "_" + System.currentTimeMillis() + ".jpg");

        // Upload the image to Firebase Storage
        imageRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Once uploaded successfully, get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        String imageUrl = downloadUri.toString();

                        // Create a map to hold project details
                        Map<String, Object> projectData = new HashMap<>();
                        projectData.put("projectName", projectname);
                        projectData.put("projectDescription", projectdescription);
                        projectData.put("projectlink",projectlink);
                        projectData.put("projectImage", imageUrl); // Store image URL

                        // Add the project data to a new document inside the "projectdetails" collection
                        userRef.collection("projectdetails")
                                .add(projectData)
                                .addOnSuccessListener(documentReference -> {
                                    // Show success message
                                    Toast.makeText(portfolioAdd_activity.this, "Project added successfully", Toast.LENGTH_SHORT).show();

                                    // Clear input fields after successful upload
                                    etportfolioname.setText("");
                                    etportfoliodescription.setText("");
                                    etportfoliolink.setText("");
                                    ivprojectpicture.setImageResource(0); // Clear image
                                })
                                .addOnFailureListener(e -> {
                                    // Show failure message if adding project data fails
                                    Toast.makeText(portfolioAdd_activity.this, "Failed to add project: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                })
                                .addOnCompleteListener(task -> {
                                    // Hide loading animation after upload completion
                                    loading_animation.setVisibility(View.INVISIBLE);
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    // Show failure message if uploading image fails
                    Toast.makeText(portfolioAdd_activity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}






