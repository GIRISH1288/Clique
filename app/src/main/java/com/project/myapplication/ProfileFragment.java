package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormatSymbols;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private TabLayout profileTab;
    private ViewPager profileViewPager;
    TextView tvProfileFullName, tvProfileUserName, tvProfileCity, tvProfileJoinedDate, tvProfileUniversityInfo;
    FirebaseAuth mAuth;
    CircleImageView profileProfilePicture;
    FirebaseFirestore db;
    String userID;
    StorageReference storageReference;
    ImageView ivProfileEditButton;
    Button btnProfileShowConnections, btnProfileShowCommunities;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        // Access toolbar and setSupportActionBar only after Fragment is attached
        Toolbar toolbar = view.findViewById(R.id.profileToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // Use getActivity() here
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");


        profileTab = view.findViewById(R.id.profileTab);
        profileViewPager = view.findViewById(R.id.profileViewPager);
        profileProfilePicture = view.findViewById(R.id.profileProfilePicture);
        tvProfileFullName = view.findViewById(R.id.tvProfileFullName);
        tvProfileUserName = view.findViewById(R.id.tvProfileUserName);
        tvProfileCity = view.findViewById(R.id.tvProfileCity);
        tvProfileJoinedDate = view.findViewById(R.id.tvProfileJoinedDate);
        ivProfileEditButton = view.findViewById(R.id.ivProfileEditButton);
        tvProfileUniversityInfo = view.findViewById(R.id.tvProfileUniversityInfo);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnProfileShowConnections = view.findViewById(R.id.btnProfileShowConnections);

        int defaultImage = R.drawable.no_dp_selected;

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileImageRef = storageReference.child("profile_images").child(mAuth.getCurrentUser().getUid());
        profileImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            // Successfully downloaded the image data as a byte array
            // Convert the byte array to a BitmapDrawable
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);

            // Set the downloaded image as the background of the CircleImageView
            profileProfilePicture.setImageDrawable(drawable);
        }).addOnFailureListener(exception -> {
            // Handle failure to download the image
            // This can occur if the user has not uploaded a profile picture yet
            profileProfilePicture.setImageResource(defaultImage);
        });
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);
           documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
         public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (isAdded() && getContext() != null) { // Check if the fragment is attached and context is not null
                    if (documentSnapshot.exists()) {
                        // Access field data if document exists
                        String fullName = documentSnapshot.getString("name");
                        String userName = documentSnapshot.getString("username");
                        String city = documentSnapshot.getString("city");
                        String university = documentSnapshot.getString("university");
                        String department = documentSnapshot.getString("department");
                        long year = documentSnapshot.getLong("registrationYear");
                        long month = documentSnapshot.getLong("registrationMonth");
                        String universityInfo = university + ", " + department;
                        String monthName = "";
                        if (month >= 0 && month <= 11) {
                            DateFormatSymbols dfs = new DateFormatSymbols();
                            String[] months = dfs.getMonths();
                            monthName = months[(int) month];
                        }
                        String joinDate = "Joined on " + monthName + " " + year;
                        tvProfileFullName.setText(fullName);
                        tvProfileUserName.setText(userName);
                        tvProfileCity.setText(city);
                        tvProfileJoinedDate.setText(joinDate);
                        tvProfileUniversityInfo.setText(universityInfo);
                    } else {

                        // Document does not exist
                        Toast.makeText(getContext(), "Document does not exist", Toast.LENGTH_LONG).show();
                    }
                } else {

                        Toast.makeText(getContext(), "Fragment is not attached", Toast.LENGTH_LONG).show();


                }
            }
        }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                if (getContext() != null) { // Check if the context is not null
                    Toast.makeText(getContext(), "Error loading Page", Toast.LENGTH_LONG).show();
                }
            }
        });


        ProfileViewPagerTabAdapter adapter = new ProfileViewPagerTabAdapter(getChildFragmentManager());
        profileViewPager.setAdapter(adapter);
        profileTab.setupWithViewPager(profileViewPager);
        ivProfileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), EditProfileMenu.class));
            }
        });
        btnProfileShowConnections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ShowConnections.class);
                intent.putExtra("userID", userID);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_share) {
            // Handle share action
            if (getContext() != null){
            Toast.makeText(requireContext(), "Share clicked", Toast.LENGTH_SHORT).show();}
            return true;
        } else if (item.getItemId() == R.id.profile_settings) {
            // Handle settings action
            Intent intent = new Intent(requireContext(), SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
