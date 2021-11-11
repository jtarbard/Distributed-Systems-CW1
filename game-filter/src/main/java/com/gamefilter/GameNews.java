package com.gamefilter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameNews implements hasTitle{
    private int id;
    private String title;
    @JsonProperty("short_description")
    private String shortDescription;
    private String thumbnail;
    @JsonProperty("main_image")
    private String mainImage;
    @JsonProperty("article_content")
    private String articleContent;
    @JsonProperty("article_url")
    private String articleUrl;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getMainImage() {
        return mainImage;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
