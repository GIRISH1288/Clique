package com.project.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePicture;
        private TextView userName;
        private TextView commentText;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.showCommentProfilePicture);
            userName = itemView.findViewById(R.id.commentUserName);
            commentText = itemView.findViewById(R.id.commentText);
        }
        public void bind(Comment comment) {
            Glide.with(itemView.getContext())
                    .load(comment.getShowCommentProfilePicture())
                    .placeholder(R.drawable.no_dp_selected)
                    .error(R.drawable.no_dp_selected)
                    .circleCrop()
                    .into(profilePicture);
            userName.setText(comment.getCommentUserName());
            commentText.setText(comment.getCommentText());
        }
    }
}
