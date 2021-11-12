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

    // GIVEN a list of objects that implement the hasTitle interface,
    // Extracts only the list of titles, potentially sorted in alphabetical order
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


    // given a list of objects of generic type that implement a title parameter,
    // returns the list of objects filtered by titles found in a list of games
    public <T extends hasTitle> List<T> getFilteredList(List<T> listToFilter, List<Game> games) {
        List<String> gameTitles = getTitles(games,false);
        List<String> listTitles = getTitles(listToFilter, false);

        // creates a new list to contain the filtered objects
        List<T> filteredList = new LinkedList<>();
        // goes through each object's title
        for (int i=0; i < listTitles.size(); i++) {
            String theListTitle = listTitles.get(i);
            // goes through each game title
            for (int j=0; j < gameTitles.size(); j++) {
                // when a matching title is found, the object is added to the filtered list
                String theGameTitle = gameTitles.get(j);
                if (theListTitle.contains(theGameTitle)) {
                    filteredList.add(listToFilter.get(i));
                    break;
                }
            }
        }

        return filteredList;
    }
}
