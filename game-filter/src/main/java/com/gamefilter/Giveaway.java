package com.gamefilter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Giveaway implements hasTitle{
    private int id;
    private String title;
    @JsonProperty("keys_left")
    private String keysLeft;
    private String thumbnail;
    @JsonProperty("main_image")
    private String mainImage;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("giveaway_url")
    private String giveawayUrl;

    public Giveaway() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getKeysLeft() {
        return keysLeft;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getMainImage() {
        return mainImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getGiveawayUrl() {
        return giveawayUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
