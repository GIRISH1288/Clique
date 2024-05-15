package com.project.myapplication;

public class Comment {
    public Comment(String showCommentProfilePicture, String commentUserName, String commentText) {
        this.showCommentProfilePicture = showCommentProfilePicture;
        this.commentUserName = commentUserName;
        this.commentText = commentText;
    }

    public String getShowCommentProfilePicture() {
        return showCommentProfilePicture;
    }

    public void setShowCommentProfilePicture(String showCommentProfilePicture) {
        this.showCommentProfilePicture = showCommentProfilePicture;
    }

    private String showCommentProfilePicture, commentUserName, commentText;

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
