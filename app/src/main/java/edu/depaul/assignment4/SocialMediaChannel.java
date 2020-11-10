package edu.depaul.assignment4;

import java.io.Serializable;

public class SocialMediaChannel implements Serializable {

    private String facebookUrl;
    private String twitterUrl;
    private String youtubeUrl;

    public SocialMediaChannel() {
//        this.facebookUrl = facebookUrl;
//        this.twitterUrl = twitterUrl;
//        this.youtubeUrl = youtubeUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    @Override
    public String toString() {
        return "SocialMediaChannel{" +
                "facebookUrl='" + facebookUrl + '\'' +
                ", twitterUrl='" + twitterUrl + '\'' +
                ", youtubeUrl='" + youtubeUrl + '\'' +
                '}';
    }
}
