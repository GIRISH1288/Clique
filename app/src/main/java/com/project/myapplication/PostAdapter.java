package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    String username;
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Posts post = postList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(post.getPostItemProfilePictureUrl())
                .placeholder(R.drawable.no_dp_selected)
                .error(R.drawable.no_dp_selected)
                .circleCrop()
                .into(holder.profilePicture);
        holder.username.setText(post.getPostItemUserName());
        holder.caption.setText(post.getPostItemCaption());
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
                            holder.likeIcon.setImageResource(R.drawable.after_liked);
                            holder.likeIcon.setEnabled(false);
                        } else {
                            // User has not liked this post yet
                            // Proceed with whatever action you want to take
                        }
                    }
                });
        Glide.with(holder.itemView.getContext())
                .load(post.getPostItemPostUrl())
                .into(holder.postImage);
        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeIcon.setImageResource(R.drawable.after_liked);
                holder.likeIcon.setEnabled(false);
                mAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();

                userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                db.collection("users").document(userID).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    username = documentSnapshot.getString("username");
                                }
                            }
                        });
                DocumentReference userRef = db.collection("users").document(post.getPostUserUserID());
                userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {


                            // Add the like to the Firestore
                            Map<String, Object> likeData = new HashMap<>();
                            likeData.put("userID", userID);
                            likeData.put("userName", username);
                            likeData.put("timestamp", FieldValue.serverTimestamp());

                            db.collection("users")
                                    .document(postList.get(position).getPostUserUserID())
                                    .collection("posts")
                                    .document(postList.get(position).getPostID())
                                    .collection("likes")
                                    .add(likeData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(holder.itemView.getContext(), "Liked!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.itemView.getContext(), "Failed to like!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
        holder.commentIcon.setOnClickListener(v -> {
            if (commentClickListener != null) {
                commentClickListener.onCommentClick(postID, postUserUserID);
            }
        });
        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowComment.class);
                intent.putExtra("postID", postID);
                Toast.makeText(holder.itemView.getContext(),postID, Toast.LENGTH_SHORT).show();
                Toast.makeText(holder.itemView.getContext(),postUserUserID, Toast.LENGTH_SHORT).show();
                intent.putExtra("postUserUserID", postUserUserID);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.bind(post);
    }
}
