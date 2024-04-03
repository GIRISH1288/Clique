package com.project.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectedUserAdapter extends RecyclerView.Adapter<ConnectedUserAdapter.UserViewHolder> {
    private List<ConnectedUser> userList;

    // Constructor to initialize userList
    public ConnectedUserAdapter(List<ConnectedUser> userList) {
        this.userList = userList;
    }

    // ViewHolder class
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView showConnectionsProfilePicture;
        private TextView showConnectionsFullName;
        private TextView showConnectionsUserName;
        private Button showConnectionsViewProfile;


        public UserViewHolder(View itemView) {
            super(itemView);
            showConnectionsProfilePicture = itemView.findViewById(R.id.showConnectionsProfilePicture);
            showConnectionsFullName = itemView.findViewById(R.id.showConnectionsFullName);
            showConnectionsViewProfile = itemView.findViewById(R.id.showConnectionsViewProfile);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_items, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ConnectedUser user = userList.get(position);
        // Bind user data to views
        holder.showConnectionsFullName.setText(user.getFullName());
        // Set profile picture using a library like Picasso or Glide
        // Example: Picasso.get().load(user.getProfilePictureUrl()).into(holder.profilePicture);
        Glide.with(holder.itemView.getContext())
                .load(user.getProfilePictureUrl())
                .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                .error(R.drawable.no_dp_selected) // Error image if loading fails
                .circleCrop() // Crop the image into a circle
                .into(holder.showConnectionsProfilePicture);
        /*
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        String connectUser = user.getUserID();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(userID);
        final String[] loggedInUser = new String[1];
        final String[] loggedUsername = new String[1];
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Access field data if document exist
                    loggedInUser[0] = documentSnapshot.getString("name");
                    loggedUsername[0] = documentSnapshot.getString("username");
                }
            }
        });
        holder.addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToken = user.getNotificationToken();
                FcmNotificationSender fcmNotificationSender = new FcmNotificationSender(userToken, loggedUsername[0], loggedInUser[0]+" wants to connect with you!", holder.itemView.getContext());
                DocumentReference documentReference = db.collection("users").document(connectUser).collection("request").document();
                Map<String, Object> addUser = new HashMap<>();
                addUser.put("userID", userID);
                db.collection("users")
                        .document(connectUser)
                        .collection("request")
                        .whereEqualTo("userID", userID)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        Toast.makeText(holder.itemView.getContext(), "Already sent request",Toast.LENGTH_LONG).show();
                                    } else {
                                        documentReference.set(addUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                } else {
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                fcmNotificationSender.SendNotification();
            }
        });

         */
        holder.showConnectionsViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewUserProfile.class);
                intent.putExtra("userID", user.getUserID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
