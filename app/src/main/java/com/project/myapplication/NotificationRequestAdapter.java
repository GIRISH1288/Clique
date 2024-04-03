package com.project.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationRequestAdapter extends RecyclerView.Adapter<NotificationRequestAdapter.NotificationRequestViewHolder> {
    private List<NotificationRequest> notificationRequestList;

    // Constructor to initialize notificationRequestList
    public NotificationRequestAdapter(List<NotificationRequest> notificationRequestList) {
        this.notificationRequestList = notificationRequestList;
    }

    // ViewHolder class
    public static class NotificationRequestViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivNotificationProfilePicture;
        private TextView tvNotificationFullName, tvNotificationUniversityDetails;
        private Button btnNotificationConnectUser, btnNotificationViewUserProfile;
        private FirebaseAuth mAuth;
        private FirebaseFirestore db;
        private String userID;

        public NotificationRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotificationProfilePicture = itemView.findViewById(R.id.ivNotificationProfilePicture);
            tvNotificationFullName = itemView.findViewById(R.id.tvNotificationFullName);
            tvNotificationUniversityDetails = itemView.findViewById(R.id.tvNotificationUniversityDetails);
            btnNotificationConnectUser = itemView.findViewById(R.id.btnNotificationConnectUser);
            btnNotificationViewUserProfile = itemView.findViewById(R.id.btnNotificationViewUserProfile);

        }
    }

    @NonNull
    @Override
    public NotificationRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRequestViewHolder holder, int position) {
        NotificationRequest notificationRequest = notificationRequestList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(notificationRequest.getProfilePictureUrl())
                .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                .error(R.drawable.no_dp_selected) // Error image if loading fails
                .circleCrop() // Crop the image into a circle
                .into(holder.ivNotificationProfilePicture);
        holder.tvNotificationFullName.setText(notificationRequest.getFullName());
        holder.tvNotificationUniversityDetails.setText(notificationRequest.getUniversityDetails());
        holder.btnNotificationConnectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnNotificationConnectUser.setEnabled(false);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userID = mAuth.getCurrentUser().getUid();
                String userRequestID = notificationRequest.getUserRequestID();
                Map<String, Object> dataMainUser = new HashMap<>();
                dataMainUser.put("userID", userRequestID);
                db.collection("users").document(userID)
                        .collection("connection")
                        .document()
                        .set(dataMainUser);
                Map<String, Object> data = new HashMap<>();
                data.put("userID", userID);
                db.collection("users").document(userRequestID)
                        .collection("connection")
                        .document()
                        .set(data);
                db.collection("users").document(userID)
                        .collection("request")
                        .whereEqualTo("userID", userRequestID)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Iterate through the result documents
                                for (DocumentSnapshot document : task.getResult()) {
                                    // Get the document ID
                                    String documentId = document.getId();

                                    // Delete the document
                                    db.collection("users")
                                            .document(userID)
                                            .collection("request")
                                            .document(documentId)
                                            .delete()
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(holder.itemView.getContext(), "Connected Successfully", Toast.LENGTH_SHORT).show();
                                                holder.btnNotificationConnectUser.setText("Connected");


                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(holder.itemView.getContext(), "connection failed", Toast.LENGTH_SHORT).show();
                                                holder.btnNotificationConnectUser.setEnabled(true);
                                            });
                                }
                            } else {
                                // Handle errors while fetching documents
                                // Log.e(TAG, "Error getting documents: ", task.getException());
                            }
                        });



            }
        });
        holder.btnNotificationViewUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewUserProfile.class);
                intent.putExtra("userID", notificationRequest.getUserRequestID());
                v.getContext().startActivity(intent);

            }
        });
        // Bind other data to views as needed
    }

    @Override
    public int getItemCount() {
        return notificationRequestList.size();
    }
}
