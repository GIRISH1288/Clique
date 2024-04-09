package com.project.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateChatUserAdapter extends RecyclerView.Adapter<CreateChatUserAdapter.UserViewHolder> {
    private final List<MessageUser> messageUserList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(String userId);
    }

    public CreateChatUserAdapter(List<MessageUser> messageUserList, OnItemClickListener mlistener) {
        this.messageUserList = messageUserList;
        this.mlistener = mlistener;
    }
    @NonNull
    @Override
    public CreateChatUserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateChatUserAdapter.UserViewHolder holder, int position) {
        MessageUser messageUser = messageUserList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(messageUser.getProfilePictureUrl())
                .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                .error(R.drawable.no_dp_selected) // Error image if loading fails
                .circleCrop() // Crop the image into a circle
                .into(holder.messagesUserProfilePicture);
        holder.messagesUserFullName.setText(messageUser.getFullName());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlistener != null) mlistener.onItemClick(messageUser.getUserID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView messagesUserProfilePicture;
        private TextView messagesUserFullName, tvLastMessage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            messagesUserProfilePicture = itemView.findViewById(R.id.messagesUserProfilePicture);
            messagesUserFullName = itemView.findViewById(R.id.messagesUserFullName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);

        }
    }
}
