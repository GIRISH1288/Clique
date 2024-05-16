package com.project.myapplication;

public class Comment {
    public Comment(String showCommentProfilePicture, String commentUserName, String commentText, String commentUserID) {
        this.showCommentProfilePicture = showCommentProfilePicture;
        this.commentUserName = commentUserName;
        this.commentText = commentText;
        this.commentUserID = commentUserID;
    }

    public String getShowCommentProfilePicture() {
        return showCommentProfilePicture;
    }

    public void setShowCommentProfilePicture(String showCommentProfilePicture) {
        this.showCommentProfilePicture = showCommentProfilePicture;
    }

    private String showCommentProfilePicture;
    private String commentUserName;
    private String commentText;

    public String getCommentUserID() {
        return commentUserID;
    }

    public void setCommentUserID(String commentUserID) {
        this.commentUserID = commentUserID;
    }

    private String commentUserID;

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
