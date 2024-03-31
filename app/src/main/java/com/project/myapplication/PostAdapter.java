package com.project.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Posts> postList;

    public PostAdapter(List<Posts> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView profilePicture;
        private TextView caption;
        private TextView username;
        private ImageView postImage;
        public PostViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.postItemProfilePicture);
            caption = itemView.findViewById(R.id.postItemCaption);
            username = itemView.findViewById(R.id.postItemUserName);
            postImage = itemView.findViewById(R.id.postItemPost);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Posts post = postList.get(position);
        // Set data to views
        if (holder instanceof PostViewHolder) {
            PostViewHolder postViewHolder = (PostViewHolder) holder;
            // Set data to views
            Glide.with(holder.itemView.getContext())
                    .load(post.getPostItemProfilePictureUrl())
                    .placeholder(R.drawable.no_dp_selected) // Placeholder image while loading
                    .error(R.drawable.no_dp_selected) // Error image if loading fails
                    .circleCrop() // Crop the image into a circle
                    .into(postViewHolder.profilePicture);
            postViewHolder.username.setText(post.getPostItemUserName());
            postViewHolder.caption.setText(post.getPostItemCaption());
            Glide.with(holder.itemView.getContext())
                    .load(post.getPostItemPostUrl())
                    .into(postViewHolder.postImage);
        }
    }
    @Override
    public int getItemCount() {
        return postList.size();
    }
}
