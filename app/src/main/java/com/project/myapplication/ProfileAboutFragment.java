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