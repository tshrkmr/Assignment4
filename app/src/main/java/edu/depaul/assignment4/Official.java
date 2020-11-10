package edu.depaul.assignment4;

import java.io.Serializable;

public class Official implements Serializable {

    private String officeName;
    private String officeHolder;
    private String officeAddress;
    private String party;
    private String officePhone;
    private String websiteUrl;
    private  String officeEmail;
    private SocialMediaChannel socialMediaChannel;
    private String photoUrl;

    public Official(String officeName) {
        this.officeName = officeName;
//        this.officeHolder = officeHolder;
//        this.officeAddress = officeAddress;
//        this.party = party;
//        this.officePhone = officePhone;
//        this.url = url;
//        this.officeEmail = officeEmail;
//        this.socialMediaChannel = socialMediaChannel;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public void setOfficeHolder(String officeHolder) {
        this.officeHolder = officeHolder;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public void setSocialMediaChannel(SocialMediaChannel socialMediaChannel) {
        this.socialMediaChannel = socialMediaChannel;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getOfficeHolder() {
        return officeHolder;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public String getParty() {
        return party;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public SocialMediaChannel getSocialMediaChannel() {
        return socialMediaChannel;
    }

    @Override
    public String toString() {
        return "Official{" +
                "officeName='" + officeName + '\'' +
                ", officeHolder='" + officeHolder + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", party='" + party + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", officeEmail='" + officeEmail + '\'' +
                ", socialMediaChannel=" + socialMediaChannel +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
