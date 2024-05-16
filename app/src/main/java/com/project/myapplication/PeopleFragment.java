package com.project.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeopleFragment extends Fragment {
    private RecyclerView postsRecyclerViewStudentFragment;
    private String username;
    private String profileImageUrl;
    private List<Posts> postList;
    FirebaseFirestore db;
    StorageReference storageReference;
    String userID;
    LikeClickListener likeClickListener;
    CommentClickListener commentClickListener;
    ViewLikeClickListener viewLikeClickListener;
    ViewCommentClickListener viewCommentClickListener;
    String postID;

    public PeopleFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_people, container, false);
        db = FirebaseFirestore.getInstance();
        postList = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        likeClickListener = new LikeClickListener(requireContext()); // 'requireContext()' returns the fragment's context
        commentClickListener = new CommentClickListener(requireContext());
        viewLikeClickListener = new ViewLikeClickListener();
        viewCommentClickListener = new ViewCommentClickListener();

        postsRecyclerViewStudentFragment = rootView.findViewById(R.id.postsRecyclerViewStudentFragment);
        postsRecyclerViewStudentFragment.setHasFixedSize(true);
        postsRecyclerViewStudentFragment.setLayoutManager(new LinearLayoutManager(requireContext()));
        db.collection("users")
                .document(userID)
                .collection("connection")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                            String connectionUserID = documentSnapshot.getString("userID");
                            if (connectionUserID != null) {
                                db.collection("users")
                                        .document(connectionUserID)
                                        .get()
                                        .addOnSuccessListener(connectionUserDoc -> {
                                            if (connectionUserDoc.exists()) {
                                                String connectionUsername = connectionUserDoc.getString("username");
                                                String connectionProfileImageUrl = connectionUserDoc.getString("profileImageUrl");

                                                db.collection("users")
                                                        .document(connectionUserID)
                                                        .collection("posts")
                                                        .get()
                                                        .addOnCompleteListener(postsTask -> {
                                                            if (postsTask.isSuccessful()) {
                                                                for (QueryDocumentSnapshot postDoc : Objects.requireNonNull(postsTask.getResult())) {
                                                                    postID = postDoc.getId();
                                                                    String caption = postDoc.getString("caption");
                                                                    String postImageUrl = postDoc.getString("imageUrl");
                                                                    Posts post = new Posts(connectionProfileImageUrl, connectionUsername, postImageUrl, caption, userID, postID);
                                                                    postList.add(post);
                                                                }
                                                                // Notify adapter when all posts are added
                                                                PostAdapter postAdapter = new PostAdapter(postList, likeClickListener, commentClickListener, viewLikeClickListener, viewCommentClickListener,requireContext());
                                                                postsRecyclerViewStudentFragment.setAdapter(postAdapter);
                                                            } else {
                                                                // Handle error fetching posts
                                                                // Log.e(TAG, "Error getting posts: ", postsTask.getException());
                                                            }
                                                        });
                                            } else {
                                                // Handle error if connection user document doesn't exist
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            // Handle failure to fetch connection user document
                                        });
                            }
                        }
                    } else {
                        // Handle error fetching connection documents
                        // Log.e(TAG, "Error getting connection documents: ", task.getException());
                    }
                });

        return rootView;
    }
}
































