package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageUserAdapter extends RecyclerView.Adapter<MessageUserAdapter.UserViewHolder> {
    private final List<MessageUser> messageUserList;
    private final Context context;

    public MessageUserAdapter(List<MessageUser> messageUserList, Context context) {
        this.messageUserList = messageUserList;
        this.context = context;
    }
    @NonNull
    @Override
    public MessageUserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageUserAdapter.UserViewHolder holder, int position) {
        MessageUser messageUser = messageUserList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(messageUser.getProfilePictureUrl())
                .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                .error(R.drawable.no_dp_selected) // Error image if loading fails
                .circleCrop() // Crop the image into a circle
                .into(holder.messagesUserProfilePicture);
        holder.messagesUserFullName.setText(messageUser.getFullName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, ChatActivity.class);
                intent.putExtra("uid", messageUser.getUserID());
                intent.putExtra("name", messageUser.getFullName());
                context.startActivity(intent);
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
