package com.project.myapplication;

public class User {
    private String username;
    private String fullName;
    private String profilePictureUrl; // URL of profile picture in Firebase Storage or download URL if stored locally

    public User() {
        // Default constructor required for Firestore
    }

    public User(String username, String fullName, String profilePictureUrl) {
        this.username = username;
        this.fullName = fullName;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
