
package com.project.myapplication;

public class portfoliostr {
    private String portfolioname;
    private String portfoliodescritpion;
    private String projectPictureUrl; // URL of profile picture in Firebase Storage or download URL if stored locally
    private String projectlink;



    public portfoliostr(String username, String fullName, String profilePictureUrl , String projectlink) {
        this.portfolioname = username;
        this.portfoliodescritpion = fullName;
        this.projectPictureUrl = profilePictureUrl;
        this.projectlink=projectlink;
    }

    public String getPortfolioname() {
        return portfolioname;
    }

    public void setPortfolioname(String portfolioname) {
        this.portfolioname = portfolioname;
    }

    public String getPortfoliodescritpion() {
        return portfoliodescritpion;
    }

    public void setPortfoliodescritpion(String portfoliodescritpion) {
        this.portfoliodescritpion = portfoliodescritpion;
    }

    public String getProjectPictureUrl() {
        return projectPictureUrl;
    }

    public void setProjectPictureUrl(String projectPictureUrl) {
        this.projectPictureUrl = projectPictureUrl;
    }
    public String getProjectlink() {
        return projectlink;
    }

    public void setProjectlink(String projectlink) {
        this.projectlink = projectlink;
    }
}