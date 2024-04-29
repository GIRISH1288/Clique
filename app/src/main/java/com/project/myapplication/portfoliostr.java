package com.project.myapplication;

public class portfoliostr {
    String projectId; // Add projectId field
    String projectname, projectdescription, projectImage ,projectlink;
    // long timestamp; // Add timestamp field
    //private long TIMESTAMP_FIELD;

    public String getProjectlink() {
        return projectlink;
    }

    public void setProjectlink(String projectlink) {
        this.projectlink = projectlink;
    }

    public portfoliostr(String projectId, String projectname, String projectdescription, String projectImage, String projectlink) {
        this.projectId = projectId;
        this.projectname = projectname;
        this.projectdescription = projectdescription;
        this.projectImage = projectImage;
        this.projectlink=projectlink;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectdescription() {
        return projectdescription;
    }

    public void setProjectdescription(String projectdescription) {
        this.projectdescription = projectdescription;
    }

    public String getProjectImage() {
        return projectImage;
    }

    public void setProjectImage(String projectImage) {
        this.projectImage = projectImage;
    }

    public String getProjectLink() {
        return projectlink;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}

/*
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTIMESTAMP_FIELD() {
        return TIMESTAMP_FIELD;
    }

    public void setTIMESTAMP_FIELD(long timestampField) {
        this.TIMESTAMP_FIELD = timestampField;
    }

 */

