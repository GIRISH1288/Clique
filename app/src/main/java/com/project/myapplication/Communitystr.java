package com.project.myapplication;

public class Communitystr {
    //communityID,communityName,communityDescription,communityCreatorinfo ,communityImage
    String communityID, communityImage;
    String communityName,  communityDescription, communityCreatorinfo ;
    public Communitystr(String communityID, String communityName, String communityDescription, String  communityCreatorinfo, String communityImage) {
        this.communityID=communityID;
        this.communityName=communityName;
        this.communityDescription=communityDescription;
        this. communityCreatorinfo= communityCreatorinfo;
        this.communityImage = communityImage;

    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getCommunityImage() {
        return communityImage;
    }

    public void setCommunityImage(String communityImage) {
        this.communityImage = communityImage;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String communityDescription) {
        this.communityDescription = communityDescription;
    }

    public String getCommunityCreatorinfo() {
        return communityCreatorinfo;
    }

    public void setCommunityCreatorinfo(String communityCreatorinfo) {
        this.communityCreatorinfo = communityCreatorinfo;
    }


}
