package com.project.myapplication;

public class NotificationRequest {
    private String universityDetails;
    private String fullName;
    private String profilePictureUrl;
    private String userRequestID;

    public NotificationRequest() {
        //default constructor
    }
    public NotificationRequest(String universityDetails, String fullName, String profilePictureUrl, String userRequestID) {
        this.universityDetails = universityDetails;
        this.fullName = fullName;
        this.profilePictureUrl = profilePictureUrl;
        this.userRequestID = userRequestID;
    }

    public String getUniversityDetails() {
        return universityDetails;
    }

    public void setUniversityDetails(String universityDetails) {
        this.universityDetails = universityDetails;
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


    public String getUserRequestID() {
        return userRequestID;
    }

    public void setUserRequestID(String userRequestID) {
        this.userRequestID = userRequestID;
    }
}
