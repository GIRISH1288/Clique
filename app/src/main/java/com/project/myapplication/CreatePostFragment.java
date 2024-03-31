package com.project.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class CreatePostFragment extends Fragment {
    ImageView ivCreatePostPost;
    EditText createPostCaption;
    Button createPostSelectFromCamera;
    Button createPostSelectFromGallery;
    FirebaseAuth mAuth;
    LottieAnimationView loading_animation;
    StorageReference storageReference;
    Button createPostAddCaption;
    Button createPostPublishPost;
    FirebaseFirestore db;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        ivCreatePostPost = view.findViewById(R.id.ivCreatePostPost);
        createPostCaption = view.findViewById(R.id.createPostCaption);
        createPostSelectFromCamera = view.findViewById(R.id.createPostSelectFromCamera);
        createPostSelectFromGallery = view.findViewById(R.id.createPostSelectFromGallery);
        createPostAddCaption = view.findViewById(R.id.createPostAddCaption);
        createPostPublishPost = view.findViewById(R.id.createPostPublishPost);
        db = FirebaseFirestore.getInstance();
        loading_animation = view.findViewById(R.id.animLoading);
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        createPostSelectFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(CreatePostFragment.this)
                        .cameraOnly()
                        .crop(3f, 4f)
                        .compress(1024)
                        .maxResultSize(1080, 1440)
                        .start(10);
            }
        });
        createPostSelectFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(CreatePostFragment.this)
                        .galleryOnly()
                        .crop(3f, 4f)
                        .compress(1024)
                        .maxResultSize(1080, 1440)
                        .start(10);
            }
        });
        createPostAddCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPostAddCaption.setVisibility(View.GONE);
                createPostCaption.setVisibility(View.VISIBLE);
            }
        });
        createPostPublishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToFirebase(uri);
            }
        });






        return view;
    }

    private void uploadImageToFirebase(Uri postUri) {
        if (postUri != null) {
            loading_animation.setVisibility(View.VISIBLE);
            String userId = mAuth.getCurrentUser().getUid();
            StorageReference userPostsRef = storageReference.child("Posts").child(userId);
            StorageReference postImageRef = userPostsRef.child("post_" + System.currentTimeMillis() + ".jpg");
            postImageRef.putFile(postUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        postImageRef.getDownloadUrl().addOnSuccessListener(downloadUri ->{
                            String downloadUrl = downloadUri.toString();
                            String caption = createPostCaption.getText().toString().trim();
                            Map<String, Object> postData = new HashMap<>();
                            postData.put("imageUrl", downloadUrl);
                            postData.put("caption",caption);
                            postData.put("timestamp", FieldValue.serverTimestamp());
                            db.collection("users")
                                    .document(userId)
                                    .collection("posts")
                                    .document()
                                    .set(postData)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(requireContext(), "Post Added", Toast.LENGTH_LONG).show();
                                    })
                                    .addOnFailureListener(b -> {
                                        Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_LONG).show();
                                    })
                                    .addOnCompleteListener(c -> {
                                        loading_animation.setVisibility(View.INVISIBLE);
                                        loadFrag(new HomeFragment());
                                    });
                        });
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null ) {
            uri = data.getData();
            Glide.with(requireContext())
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(ivCreatePostPost);
            ivCreatePostPost.setVisibility(View.VISIBLE);
            createPostAddCaption.setVisibility(View.VISIBLE);
            createPostPublishPost.setVisibility(View.VISIBLE);
        }
    }
    public void loadFrag(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.bottomNavigationFrameLayout,fragment);

        ft.commit();
    }
}