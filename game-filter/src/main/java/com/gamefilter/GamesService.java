package com.gamefilter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;

/**This service class implements the application logic, which can be utilised in the
 * controller classes as required.*/
@Service
public class GamesService {
    private HttpEntity headerEntity;

    public GamesService() {
        // building the header that needs to be attached to the requests
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "mmo-games.p.rapidapi.com");
        headers.set("x-rapidapi-key", "daa42aca34msh04b68d62611460dp193a8ejsn93d9f47b24c6");
        this.headerEntity = new HttpEntity(headers);
    }

    // gets the header entity for all requests to the external API, built in the constructor of this class
    public HttpEntity getHeaderEntity() {
        return headerEntity;
    }

    // from a list of objects that implement the hasTitle interface, extracts only the list of titles,
    // potentially sorted in alphabetical order
    public <T extends hasTitle> List<String> getTitles(List<T> oList, boolean filterFlag) {
        List<String> titles = new LinkedList<>();
        for (int i=0; i < oList.size(); i++) {
            titles.add(oList.get(i).getTitle());
        }
        if (filterFlag) {
            Collections.sort(titles);
        }
        return titles;
    }

    // extracts the unique genres across the list of all game objects
    public List<String> getUniqueGenresList(List<Game> games) {
        List<String> uniqueGenres = new LinkedList<>();
        /*the list containing repeated keywords or categories not provided in the documentation
        * of the external service to filter games by category: [https://rapidapi.com/digiwalls/api/mmo-games/]*/
        List<String> genresToExclude = Arrays.asList(" MMORPG", "ARPG", "Card Game", "Battle Royale");
        // loops through each game
        for (int i=0; i < games.size(); i++) {
            String theGenre = games.get(i).getGenre();
            // Adds the genre to the list of unique genres if not already present and is not a genre to be excluded
            if (!uniqueGenres.contains(theGenre) && (!genresToExclude.contains(theGenre))) {
                uniqueGenres.add(theGenre);
            }
        }
        return uniqueGenres;
    }

    public List<GameNews> getFilteredNews(List<GameNews> news, List<Game> games) {
        List<String> gameTitles = getTitles(games,false);
        List<String> newsTitles = getTitles(news, false);

        List<GameNews> filteredNews = new LinkedList<>();
        for (int i=0; i < newsTitles.size(); i++) {
            String theNewsTitle = newsTitles.get(i);
            for (int j=0; j < gameTitles.size(); j++) {
                String theGameTitle = gameTitles.get(j);
                if (theNewsTitle.contains(theGameTitle)) {
                    filteredNews.add(news.get(i));
                    break;
                }
            }
        }

        return filteredNews;
    }
}
