package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DateFormatSymbols;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    private TabLayout profileTab;
    private ViewPager profileViewPager;
    TextView tvProfileFullName, tvProfileUserName, tvProfileCity, tvProfileJoinedDate;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userID;
    ImageView ivProfileEditButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.profileToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("");
        profileTab = view.findViewById(R.id.profileTab);
        profileViewPager = view.findViewById(R.id.profileViewPager);
        tvProfileFullName = view.findViewById(R.id.tvProfileFullName);
        tvProfileUserName = view.findViewById(R.id.tvProfileUserName);
        tvProfileCity = view.findViewById(R.id.tvProfileCity);
        tvProfileJoinedDate = view.findViewById(R.id.tvProfileJoinedDate);
        ivProfileEditButton = view.findViewById(R.id.ivProfileEditButton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Access field data
                    String fullName = documentSnapshot.getString("name");
                    String userName = documentSnapshot.getString("userName");
                    String city = documentSnapshot.getString("city");
                    long year = documentSnapshot.getLong("registrationYear");
                    long month = documentSnapshot.getLong("registrationMonth");

                    // Convert month number to month name
                    String monthName = "";
                    if (month >= 1 && month <= 12) {
                        DateFormatSymbols dfs = new DateFormatSymbols();
                        String[] months = dfs.getMonths();
                        monthName = months[(int) (month - 1)]; // Months array is zero-based
                    }

                    // Construct the join date string
                    String joinDate = "Joined on " + monthName + " " + year;
                    // Access other fields as needed

                    // Now you can use the retrieved data
                    tvProfileFullName.setText(fullName);
                    tvProfileUserName.setText(userName);
                    tvProfileCity.setText(city);
                    tvProfileJoinedDate.setText(joinDate);
                    // Set other UI elements accordingly
                } else {
                    // Document does not exist
                    Toast.makeText(requireContext(), "error1", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(requireContext(), "Error2", Toast.LENGTH_LONG).show();
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
            Toast.makeText(requireContext(), "Share clicked", Toast.LENGTH_SHORT).show();
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
