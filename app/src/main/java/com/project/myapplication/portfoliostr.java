package com.project.myapplication;

public class portfoliostr {
    String projectname, projectdescription, projectImage;
    long timestamp; // Add timestamp field
    private long TIMESTAMP_FIELD;

    public portfoliostr(String projectname, String projectdescription, String projectImage, long timestamp) {
        this.projectname = projectname;
        this.projectdescription = projectdescription;
        this.projectImage = projectImage;
        this.timestamp = timestamp;
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
}
