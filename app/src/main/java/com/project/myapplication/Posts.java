package com.project.myapplication;

import android.widget.ImageView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Posts {
    private String postItemUserName;
    private String postItemPostUrl, postItemProfilePictureUrl, postItemCaption;

    // Constructor
    public Posts(String postItemProfilePictureUrl, String postItemUserName, String postItemPostUrl, String postItemCaption) {
        this.postItemProfilePictureUrl = postItemProfilePictureUrl;
        this.postItemUserName = postItemUserName;
        this.postItemPostUrl = postItemPostUrl;
        this.postItemCaption = postItemCaption;
    }

    // Getters and setters
    public String getPostItemProfilePictureUrl() {
        return postItemProfilePictureUrl;
    }

    public void setPostItemProfilePictureUrl(String postItemProfilePictureUrl) {
        this.postItemProfilePictureUrl = postItemProfilePictureUrl;
    }
    public String getPostItemCaption() {
        return postItemCaption;
    }

    public void setPostItemCaption(String postItemCaption) {
        this.postItemCaption = postItemCaption;
    }

    public String getPostItemUserName() {
        return postItemUserName;
    }

    public void setPostItemUserName(String postItemUserName) {
        this.postItemUserName = postItemUserName;
    }

    public String getPostItemPostUrl() {
        return postItemPostUrl;
    }

    public void setPostItemPost(String postItemPostUrl) {
        this.postItemPostUrl= postItemPostUrl;
    }
}

