package com.project.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    // Constructor to initialize userList
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    // ViewHolder class
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePicture;
        private TextView fullName;
        private TextView username;
        private ImageButton addFriendButton;


        public UserViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.searchProfilePicture);
            fullName = itemView.findViewById(R.id.searchUserFullName);
            username = itemView.findViewById(R.id.searchUserUserName);
            addFriendButton = itemView.findViewById(R.id.searchAddFriend);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_item_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        // Bind user data to views
        holder.fullName.setText(user.getFullName());
        holder.username.setText(user.getUsername());
        // Set profile picture using a library like Picasso or Glide
        // Example: Picasso.get().load(user.getProfilePictureUrl()).into(holder.profilePicture);
        Glide.with(holder.itemView.getContext())
                .load(user.getProfilePictureUrl())
                .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                .error(R.drawable.no_dp_selected) // Error image if loading fails
                .circleCrop() // Crop the image into a circle
                .into(holder.profilePicture);
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
                holder.addFriendButton.setEnabled(false);
                FcmNotificationSender fcmNotificationSender = new FcmNotificationSender(userToken, loggedUsername[0], loggedInUser[0]+" wants to connect with you!", holder.itemView.getContext());
                DocumentReference documentReference = db.collection("users").document(connectUser).collection("request").document();
                db.collection("users")
                        .document(userID)
                        .collection("request")
                        .whereEqualTo("userID", connectUser)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        Toast.makeText(holder.itemView.getContext(), "Accept pending request",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                Map<String, Object> addUser = new HashMap<>();
                addUser.put("userID", userID);
                db.collection("users")
                        .document(userID)
                        .collection("connection")
                        .whereEqualTo("userID", connectUser)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        Toast.makeText(holder.itemView.getContext(), "connected already",Toast.LENGTH_LONG).show();
                                    } else {
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
                                }
                            }
                        });


            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
