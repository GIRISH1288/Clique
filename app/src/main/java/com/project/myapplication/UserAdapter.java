package com.project.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
