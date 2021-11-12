package com.gamefilter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**Container wrapper class for response object resource from calling the
* external service (MMO GAMES) API endpoint: https://mmo-games.p.rapidapi.com/games
* The endpoint returns a collection of Game objects each containing the
* parameter set provided in the definition of the below class. */

public class Game implements hasTitle{
    //defining properties for resource object below
    private String developer;
    /*to indicate the server that we are changing the property name encoding of
    * the returned object in order to conform to java naming conventions.
    * Therefore, the server will read the JSON property 'game_url' but will
    * set the object property to gameUrl instead. This is necessary to avoid
    * incorrectly null values.*/
    @JsonProperty("game_url")
    private String gameUrl;
    private String genre;
    private int id;
    private String platform;
    @JsonProperty("profile_url")
    private String profileUrl;
    private String publisher;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("short_description")
    private String shortDescription;
    private String thumbnail;
    private String title;

    public Game() {}

    public String getDeveloper() {
        return developer;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    public String getPlatform() {
        return platform;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
