package com.project.myapplication;

public class User {
    private String username;
    private String fullName;
    private String profilePictureUrl; // URL of profile picture in Firebase Storage or download URL if stored locally
    private String notificationToken;
    private String userID;





    public User() {
        // Default constructor required for Firestore
    }

    public User(String username, String fullName, String profilePictureUrl, String notificationToken, String userID) {
        this.username = username;
        this.fullName = fullName;
        this.profilePictureUrl = profilePictureUrl;
        this.notificationToken = notificationToken;
        this.userID = userID;
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
    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
