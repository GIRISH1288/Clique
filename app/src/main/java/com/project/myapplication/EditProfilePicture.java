package com.project.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfilePicture extends AppCompatActivity {
    CircleImageView ivEditProfilePicture;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore db;
    Button btnEditSelectProfilePicture, btnEditRemoveProfilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_picture);

        ivEditProfilePicture = findViewById(R.id.ivEditProfilePicture);
        btnEditSelectProfilePicture = findViewById(R.id.btnEditSelectProfilePicture);
        btnEditRemoveProfilePicture = findViewById(R.id.btnEditRemoveProfilePicture);
        int defaultImage = R.drawable.no_dp_selected;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileImageRef = storageReference.child("profile_images").child(mAuth.getCurrentUser().getUid());
        profileImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            // Successfully downloaded the image data as a byte array
            // Convert the byte array to a BitmapDrawable
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);

            // Set the downloaded image as the background of the CircleImageView
            ivEditProfilePicture.setImageDrawable(drawable);
        }).addOnFailureListener(exception -> {
            // Handle failure to download the image
            // This can occur if the user has not uploaded a profile picture yet
            ivEditProfilePicture.setImageResource(defaultImage);
        });


        btnEditSelectProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfilePicture.this)
                        .cropSquare()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(10);
            }
        });
        LottieAnimationView loadingAnimation = findViewById(R.id.animLoadingRemove);
        btnEditRemoveProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the loading animation
                loadingAnimation.setVisibility(View.VISIBLE);
                loadingAnimation.playAnimation();

                // Delay the execution of profile picture removal
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Remove the profile picture from Firebase Storage
                        String userId = mAuth.getCurrentUser().getUid();
                        StorageReference imageRef = storageReference.child("profile_images").child(userId);
                        imageRef.delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Delete successful, set the default image
                                    ivEditProfilePicture.setImageResource(defaultImage);
                                    // Also update the profile image URL in Firestore to null or any default value
                                    db.collection("users").document(userId)
                                            .update("profileImageUrl", null) // or set it to a default URL
                                            .addOnSuccessListener(aVoid1 -> {
                                                Toast.makeText(EditProfilePicture.this, "Profile picture removed", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(EditProfilePicture.this, "Failed to remove profile picture", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnCompleteListener(task -> {
                                                // Hide the loading animation after completion
                                                loadingAnimation.cancelAnimation();
                                                loadingAnimation.setVisibility(View.GONE);
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    // Failed to delete the image
                                    Toast.makeText(EditProfilePicture.this, "Failed to remove profile picture", Toast.LENGTH_SHORT).show();
                                    // Hide the loading animation
                                    loadingAnimation.cancelAnimation();
                                    loadingAnimation.setVisibility(View.GONE);
                                });
                    }
                }, 3000); // Delay for 3 seconds
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null ) {
            Uri uri = data.getData();
            uploadImageToFirebase(uri);
            ivEditProfilePicture.setImageURI(uri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            // Get the current user ID
            String userId = mAuth.getCurrentUser().getUid();

            // Define the path in Firebase Storage where the image will be stored
            StorageReference imageRef = storageReference.child("profile_images").child(userId);

            // Upload the image to Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        // Get the download URL of the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                            // Uri contains the download URL of the uploaded image
                            String downloadUrl = downloadUri.toString();
                            // Now you can use this download URL to store it in the database
                            // For example, update the user's profile in Firestore
                            db.collection("users").document(userId)
                                    .update("profileImageUrl", downloadUrl)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(EditProfilePicture.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        ivEditProfilePicture.setImageURI(imageUri);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(EditProfilePicture.this, "Failed to upload on firestore", Toast.LENGTH_SHORT).show();
                                    });
                            // Now you can use this download URL to store it in the database or perform other actions
                        }).addOnFailureListener(exception -> {
                            Toast.makeText(EditProfilePicture.this, "URl downloading failed", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EditProfilePicture.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}