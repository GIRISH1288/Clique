package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormatSymbols;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewUserProfile extends AppCompatActivity {
    String viewUserID;
    CircleImageView viewProfileProfilePicture;
    TextView viewProfileFullName, viewProfileUserName, viewProfileCity, viewProfileJoinedDate, viewProfileUniversityDetails;
    Button viewProfileConnections, viewProfileCommunities;
    TabLayout viewProfileTab;
    ViewPager viewProfileViewPager;
    FirebaseAuth mAuth;
    ImageButton viewProfileMessageUser;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);
        Intent intent = getIntent();
        viewUserID = intent.getStringExtra("userID");
        viewProfileProfilePicture = findViewById(R.id.viewProfileProfilePicture);
        viewProfileFullName = findViewById(R.id.viewProfileFullName);
        viewProfileUserName = findViewById(R.id.viewProfileUserName);
        viewProfileMessageUser = findViewById(R.id.viewProfileMessageUser);
        viewProfileCity = findViewById(R.id.viewProfileCity);
        viewProfileJoinedDate = findViewById(R.id.viewProfileJoinedDate);
        viewProfileUniversityDetails = findViewById(R.id.viewProfileUniversityDetails);
        viewProfileConnections = findViewById(R.id.viewProfileConnections);
        viewProfileCommunities = findViewById(R.id.viewProfileCommunities);
        viewProfileTab = findViewById(R.id.viewProfileTab);
        viewProfileViewPager = findViewById(R.id.viewProfileViewPager);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(viewUserID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Access field data if document exist
                    String fullName = documentSnapshot.getString("name");
                    String userName = documentSnapshot.getString("username");
                    String city = documentSnapshot.getString("city");
                    String university = documentSnapshot.getString("university");
                    String department = documentSnapshot.getString("department");
                    String profileImageUrl = documentSnapshot.getString("profileImageUrl");
                    long year = documentSnapshot.getLong("registrationYear");
                    long month = documentSnapshot.getLong("registrationMonth");
                    String universityInfo = university + ", " + department;
                    String monthName = "";
                    if (month >= 0 && month <= 11) {
                        DateFormatSymbols dfs = new DateFormatSymbols();
                        String[] months = dfs.getMonths();
                        monthName = months[(int) (month)];
                    }
                    String joinDate = "Joined on " + monthName + " " + year;
                    viewProfileFullName.setText(fullName);
                    viewProfileUserName.setText(userName);
                    viewProfileCity.setText(city);
                    viewProfileJoinedDate.setText(joinDate);
                    viewProfileUniversityDetails.setText(universityInfo);
                    Glide.with(ViewUserProfile.this)
                            .load(profileImageUrl)
                            .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                            .error(R.drawable.no_dp_selected) // Error image if loading fails
                            .circleCrop() // Crop the image into a circle
                            .into(viewProfileProfilePicture);
                } else {
                    // Document does not exist
                    Toast.makeText(ViewUserProfile.this, "error1", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(ViewUserProfile.this, "Error loading Page", Toast.LENGTH_LONG).show();
            }
        });
        viewProfileConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUserProfile.this, ShowConnections.class);
                intent.putExtra("userID", viewUserID);
                v.getContext().startActivity(intent);
            }
        });
        viewProfileCommunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewProfileMessageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewUserProfile.this,"directing toward chat activity", Toast.LENGTH_LONG).show();
            }
        });




    }
}