package com.project.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormatSymbols;

/*
public class ProfileAboutFragment extends Fragment {
    EditText etabout, etskills;
    TextView tvabout, tvskills;
    Button btnsetabout;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_edit_about_section, container, false);
        View aboutSectionView = inflater.inflate(R.layout.fragment_profile_about, null);

        etabout = rootView.findViewById(R.id.etabout);
        etskills = rootView.findViewById(R.id.etskills);
        btnsetabout =rootView.findViewById(R.id.btnsetabout);
        tvabout = aboutSectionView.findViewById(R.id.tvabout);
        tvskills = aboutSectionView.findViewById(R.id.tvskills);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        btnsetabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aboutText = etabout.getText().toString().trim();
                String skillsText = etskills.getText().toString().trim();

                tvabout.setText(aboutText);
                tvskills.setText(skillsText);

                // Store data in Firebase
                if (!aboutText.isEmpty() && !skillsText.isEmpty()) {
                    databaseReference.child("about").setValue(aboutText);
                    databaseReference.child("skills").setValue(skillsText);
                }
            }
        });

        return rootView;
    }
}

 */
public class ProfileAboutFragment extends Fragment {

    TextView tvabout, tvskills;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userID;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_about, container, false);

        tvabout = rootView.findViewById(R.id.tvabout);
        tvskills = rootView.findViewById(R.id.tvskills);
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Access field data if document exist
                    String about = documentSnapshot.getString("about");
                    String skills = documentSnapshot.getString("skills");
                    tvabout.setText(about);
                    tvskills.setText(skills);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(requireContext(), "Error loading about page", Toast.LENGTH_LONG).show();
            }
        });




        return rootView;
    }
}
 //girish