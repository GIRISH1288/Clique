package com.project.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Posts> postList;
    private OnLikeClickListener likeClickListener;
    private OnCommentClickListener commentClickListener;
    private OnViewLikeClickListener viewLikeClickListener;
    private OnViewCommentClickListener viewCommentClickListener;
    private String postUserUserID;
    private String postID;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Context context;



    public PostAdapter(List<Posts> postList, OnLikeClickListener likeClickListener, OnCommentClickListener commentClickListener, OnViewLikeClickListener viewLikeClickListener, OnViewCommentClickListener viewCommentClickListener, Context context) {
        this.postList = postList;
        this.likeClickListener = likeClickListener;
        this.commentClickListener = commentClickListener; // Initialize the comment click listener
        this.viewLikeClickListener = viewLikeClickListener;
        this.viewCommentClickListener = viewCommentClickListener;
        this.context =context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Posts post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface OnLikeClickListener {
        void onLikeClick(int position, List<Posts> postList);
    }
    public interface OnCommentClickListener {
        void onCommentClick(String postID, String postUserUserID);

        void onCommentClick();
    }
    public interface OnViewLikeClickListener {
        void onViewLikeClick(int position, List<Posts> postList, Context context);
    }
    public interface OnViewCommentClickListener {
        void onViewCommentClick(int position, List<Posts> postList, Context context);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView profilePicture;
        private TextView caption;
        private TextView username;
        private ImageView postImage;
        private ImageView likeIcon;
        private ImageView commentIcon;
        private TextView viewLikes;
        private TextView viewComments;

        public PostViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.postItemProfilePicture);
            caption = itemView.findViewById(R.id.postItemCaption);
            username = itemView.findViewById(R.id.postItemUserName);
            postImage = itemView.findViewById(R.id.postItemPost);
            likeIcon = itemView.findViewById(R.id.postItemLikeIcon);
            viewLikes = itemView.findViewById(R.id.postItemViewLikes);
            viewLikes.setOnClickListener(this);
            viewComments = itemView.findViewById(R.id.postItemViewComments);
            likeIcon.setOnClickListener(this);
            commentIcon = itemView.findViewById(R.id.postItemCommentIcon);
            commentIcon.setOnClickListener(this);
        }

        public void bind(Posts post) {
            Glide.with(itemView.getContext())
                    .load(post.getPostItemProfilePictureUrl())
                    .placeholder(R.drawable.no_dp_selected)
                    .error(R.drawable.no_dp_selected)
                    .circleCrop()
                    .into(profilePicture);
            username.setText(post.getPostItemUserName());
            caption.setText(post.getPostItemCaption());
            postUserUserID = post.getPostUserUserID();
            postID = post.getPostID();
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            userID = mAuth.getCurrentUser().getUid();
            db.collection("users")
                            .document(postUserUserID)
                                    .collection("posts")
                                            .document(postID)
                                                    .collection("likes")
                                                            .get()
                                                                    .addOnCompleteListener(task -> {
                                                                        if (task.isSuccessful()) {
                                                                            boolean userLiked = false;
                                                                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                                                                String likedUserID = documentSnapshot.getString("userID");
                                                                                if (likedUserID != null && likedUserID.equals(userID)) {
                                                                                    // User has already liked this post, so set the flag to true
                                                                                    userLiked = true;
                                                                                    break; // Stop the loop
                                                                                }
                                                                            }
                                                                            if (userLiked) {
                                                                                likeIcon.setImageResource(R.drawable.after_liked);
                                                                                likeIcon.setEnabled(false);
                                                                            } else {
                                                                                // User has not liked this post yet
                                                                                // Proceed with whatever action you want to take
                                                                            }
                                                                        }
                                                                    });

            Glide.with(itemView.getContext())
                    .load(post.getPostItemPostUrl())
                    .into(postImage);
            likeIcon.setOnClickListener(v -> {
                if (likeClickListener != null) {
                    likeClickListener.onLikeClick(getAdapterPosition(), postList);
                    likeIcon.setImageResource(R.drawable.after_liked);
                    likeIcon.setEnabled(false);
                }
            });
            commentIcon.setOnClickListener(v -> {
                if (commentClickListener != null) {
                    commentClickListener.onCommentClick(postID, postUserUserID);
                }
            });
            viewLikes.setOnClickListener(v -> {
                if (viewLikeClickListener != null) {
                    viewLikeClickListener.onViewLikeClick(getAdapterPosition(), postList, context);
                    Toast.makeText(itemView.getContext(), "touched on click", Toast.LENGTH_LONG).show();
                }
            });
            viewComments.setOnClickListener(v -> {
                if (viewCommentClickListener != null) {
                    viewCommentClickListener.onViewCommentClick(getAdapterPosition(), postList, context);
                }
            });
        }


        @Override
        public void onClick(View v) {

        }
    }
}
