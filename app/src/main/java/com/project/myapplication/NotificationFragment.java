package com.project.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    private RecyclerView notificationRecyclerView;
    private NotificationRequestAdapter notificationRequestAdapter;
    private List<NotificationRequest> notificationRequestList;
    private FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        notificationRecyclerView = rootView.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        notificationRequestList = new ArrayList<>();

        db.collection("users").document(userID).collection("request")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String userRequestID = documentSnapshot.getString("userID");
                            db.collection("users")
                                    .document(userRequestID)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String fullName = documentSnapshot.getString("name");
                                            String university = documentSnapshot.getString("university");
                                            String department = documentSnapshot.getString("department");
                                            String universityDetails = university + ", " + department;
                                            String profileImageUrl = documentSnapshot.getString("profileImageUrl");
                                            NotificationRequest notificationRequest = new NotificationRequest(universityDetails, fullName, profileImageUrl, userRequestID);
                                            notificationRequestList.add(notificationRequest);

                                            // Notify adapter of data change after adding each item
                                            notificationRequestAdapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                        // Initialize and set adapter after adding all items
                        notificationRequestAdapter = new NotificationRequestAdapter(notificationRequestList);
                        notificationRecyclerView.setAdapter(notificationRequestAdapter);
                    } else {
                        Toast.makeText(requireContext(), "Failed to reload request", Toast.LENGTH_LONG).show();
                    }
                });

        return rootView;
    }
}