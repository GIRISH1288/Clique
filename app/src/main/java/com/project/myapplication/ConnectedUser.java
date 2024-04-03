package com.project.myapplication;

public class ConnectedUser {
    private String fullName;
    private String profilePictureUrl; // URL of profile picture in Firebase Storage or download URL if stored locally
    private String userID;





    public ConnectedUser() {
        // Default constructor required for Firestore
    }

    public ConnectedUser(String fullName, String profilePictureUrl, String userID) {
        this.fullName = fullName;
        this.profilePictureUrl = profilePictureUrl;
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
