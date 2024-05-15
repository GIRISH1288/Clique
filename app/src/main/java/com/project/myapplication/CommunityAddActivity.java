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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
public class CommunityAddActivity extends AppCompatActivity {
    EditText etcommunityname, etcommunitydescription, etcommunitycreatorinfo;
    Button btnaddcommunity;
    ImageView ivcommunitypic;
    View addcommunityphoto;
    LottieAnimationView animLoading;
    Uri uri;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String CommunityID, Communityname, Communitydetail, Creatorinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_add);
        EdgeToEdge.enable(this);

        etcommunityname = findViewById(R.id.etcommunityname);
        etcommunitydescription = findViewById(R.id.etcommunitydescription);
        etcommunitycreatorinfo = findViewById(R.id.etcommunitycreatorinfo);
        btnaddcommunity = findViewById(R.id.btnaddcommunity);
        ivcommunitypic = findViewById(R.id.ivcommunitypic);
        addcommunityphoto = findViewById(R.id.addcommunityphoto);
        animLoading = findViewById(R.id.animLoading);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance(); // Initialize mAuth here
        CommunityID = mAuth.getCurrentUser().getUid();

        addcommunityphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivcommunitypic.setVisibility(View.VISIBLE);
                ImagePicker.Companion.with(CommunityAddActivity.this)
                        .galleryOnly()
                        .crop(3f, 4f)
                        .compress(1024)
                        .maxResultSize(1080, 1440)
                        .start(10);
            }
        });

        btnaddcommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get project name and description
                Communityname = etcommunityname.getText().toString().trim();
                Communitydetail = etcommunitydescription.getText().toString().trim();
                Creatorinfo = etcommunitycreatorinfo.getText().toString().trim();

                // Check if project name, description, and image are empty
                if (TextUtils.isEmpty(Communityname) || TextUtils.isEmpty(Communitydetail) ||TextUtils.isEmpty( Creatorinfo)|| uri == null) {
                    Toast.makeText(CommunityAddActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are filled, start uploading to Firebase
                    uploadCommunityToFirebase();

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
                    .into(ivcommunitypic);
            ivcommunitypic.setVisibility(View.VISIBLE);
        }
    }

    private void uploadCommunityToFirebase() {
        animLoading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Create a reference to the storage location
        StorageReference imageRef = storageRef.child("community").child(System.currentTimeMillis() + ".jpg");

        imageRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        String imageUrl = downloadUri.toString();

                        // Create a map to hold community details
                        Map<String, Object> communityData = new HashMap<>();
                        communityData.put("Communityname", Communityname);
                        communityData.put("Communitydetail", Communitydetail);
                        communityData.put("Creatorinfo", Creatorinfo);
                        communityData.put("communityImage", imageUrl); // Store image URL

                        // Add the community data directly to the "communities" collection
                        db.collection("communities")
                                .add(communityData)
                                .addOnSuccessListener(documentReference -> {
                                    // Show success message
                                    Toast.makeText(CommunityAddActivity.this, "Community added successfully", Toast.LENGTH_SHORT).show();

                                    // Clear input fields after successful upload
                                    etcommunityname.setText("");
                                    etcommunitydescription.setText("");
                                    etcommunitycreatorinfo.setText("");
                                    ivcommunitypic.setImageResource(0); // Clear image
                                })
                                .addOnFailureListener(e -> {
                                    // Show failure message if adding community data fails
                                    Toast.makeText(CommunityAddActivity.this, "Failed to add community: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                })
                                .addOnCompleteListener(task -> {
                                    // Hide loading animation after upload completion
                                    animLoading.setVisibility(View.INVISIBLE);
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    // Show failure message if uploading image fails
                    Toast.makeText(CommunityAddActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}


/*
addcommunityphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivcommunitypic.setVisibility(View.VISIBLE);
                ImagePicker.Companion.with(CommunityAddActivity.this)
                        .galleryOnly()
                        .crop(3f, 4f)
                        .compress(1024)
                        .maxResultSize(1080, 1440)
                        .start(10);
            }
        });

        btnaddcommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Communityname = etcommunityname.getText().toString().trim();
                Communitydetail = etcommunitydescription.getText().toString().trim();
                Creatorinfo = etcommunitycreatorinfo.getText().toString().trim();
                if (TextUtils.isEmpty(Communityname) || TextUtils.isEmpty(Communitydetail) || TextUtils.isEmpty(Creatorinfo) || uri == null) {
                    Toast.makeText(CommunityAddActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
                }
                 else {

                  uploadCommunityToFirebase();
                }
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
                            .into(ivcommunitypic);
                    ivcommunitypic.setVisibility(View.VISIBLE);
                }
            }

            private void uploadCommunityToFirebase() {
                animLoading.setVisibility(View.VISIBLE);
                String userId = mAuth.getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                DocumentReference userRef = db.collection("community").document(userId);

                StorageReference imageRef = storageRef.child("community").child(userId + "_" + System.currentTimeMillis() + ".jpg");

                imageRef.putFile(uri)
                        .addOnSuccessListener(taskSnapshot -> {
                            imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                                String imageUrl = downloadUri.toString();

                                Map<String, Object> communityData = new HashMap<>();
                                communityData.put("communityname", Communityname);
                                communityData.put("communitydescription", Communitydetail);
                                communityData.put("creatorinfo", Creatorinfo);
                                communityData.put("communityimage", imageUrl);

                                userRef
                                        .set(communityData)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(CommunityAddActivity.this, "Community added successfully", Toast.LENGTH_SHORT).show();
                                            etcommunityname.setText("");
                                            etcommunitydescription.setText("");
                                            etcommunitycreatorinfo.setText("");
                                            ivcommunitypic.setImageResource(0);
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(CommunityAddActivity.this, "Failed to add community: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnCompleteListener(task -> {
                                            animLoading.setVisibility(View.INVISIBLE);
                                        });
                            });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(CommunityAddActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
 */
