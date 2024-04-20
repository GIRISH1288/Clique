package com.project.myapplication;

public class Posts {
    private String postItemUserName;
    private String postItemPostUrl, postItemProfilePictureUrl, postItemCaption, postUserUserID, postID;

    // Constructor
    public Posts(String postItemProfilePictureUrl, String postItemUserName, String postItemPostUrl, String postItemCaption, String postUserUserID, String postID) {
        this.postItemProfilePictureUrl = postItemProfilePictureUrl;
        this.postItemUserName = postItemUserName;
        this.postItemPostUrl = postItemPostUrl;
        this.postItemCaption = postItemCaption;
        this.postUserUserID = postUserUserID;
        this.postID = postID;
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

    public String getPostUserUserID() {
        return postUserUserID;
    }

    public void setPostUserUserID(String postUserUserID) {
        this.postUserUserID = postUserUserID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}

