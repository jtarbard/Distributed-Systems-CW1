package com.gamefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**The endpoint encodings performing the appropriate application(business) logic
 * are mapped(exposed) in this Controller class.*/
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GamesController {
    // Listens to the application logic developed in the gamesServices class
    @Autowired
    public GamesService gamesService;

    // Gets the unique genres encountered across all games collected from
    // the external API
    @RequestMapping(method = RequestMethod.GET, value = "/all/genres")
    public ResponseEntity<List<String>> getGenres() {
        // the url for the external API endpoint to get the list of all games
        String url = "https://mmo-games.p.rapidapi.com/games";
        // attaches the expected header for the external API endpoint
        HttpEntity entity = gamesService.getHeaderEntity();

        // calls the external API endpoint to get all games in the MMO GAMES database
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Game[]> response = restTemplate.exchange(
            url, HttpMethod.GET, entity, Game[].class);

        // server responded with OK, therefore returned the list of game objects
        if (response.getStatusCode() == HttpStatus.OK) {
            List<Game> allGames = Arrays.asList(response.getBody());
            // from the games, extracts the unique genres
            return new ResponseEntity<>(gamesService.getUniqueGenresList(allGames),HttpStatus.OK);
        // something went wrong and the external API returned a bad response
        } else {
            return new ResponseEntity<>(Arrays.asList(
                "External API at endpoint: https://mmo-games.p.rapidapi.com/games did not respond"),
                HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/news/filter")
    @ResponseBody
    public ResponseEntity<List<GameNews>> getFilteredNews(@RequestParam String genre) {
        // the url to get games matching the specified genre category is the same
        // as the url to get all games; we simply need to attach the category(i.e. genre) parameter to it,
        // as defined in the external API. Note that we already supply an endpoint to get possible
        // genre specifications, therefore we do not perform validation checks in this method.
        // If request fails, the client did not conform to API specification and an error will be raised.
        String gamesUrl = "https://mmo-games.p.rapidapi.com/games?category={genre}";
        // attaches the expected header for the external API endpoint
        HttpEntity entity = gamesService.getHeaderEntity();

        // calls the external API endpoint to get the games filtered by the specified genre
        RestTemplate restTemplateGames = new RestTemplate();
        ResponseEntity<Game[]> responseGamesEndpoint;
        try {
            responseGamesEndpoint = restTemplateGames.exchange(
                gamesUrl, HttpMethod.GET, entity, Game[].class, genre);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Game> filteredGames = null;
        // server responded with OK, therefore returned the list of filtered game objects
        if (responseGamesEndpoint.getStatusCode() == HttpStatus.OK) {
            filteredGames = Arrays.asList(responseGamesEndpoint.getBody());
        } else {
            // user supplied an invalid genre token for filtering or external server is unavailable,
            // therefore returns a BAD_REQUEST response.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String allNewsUrl = "https://mmo-games.p.rapidapi.com/latestnews";

        // calls the external API endpoint to get all game related news in the MMO GAMES database
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GameNews[]> responseNewsEndpoint = restTemplate.exchange(
            allNewsUrl, HttpMethod.GET, entity, GameNews[].class);

        // external server responded with OK, therefore all game news were extracted.
        if (responseNewsEndpoint.getStatusCode() == HttpStatus.OK) {
            List<GameNews> allNews = Arrays.asList(responseNewsEndpoint.getBody());
            // if there are any news provided from the external API
            if (allNews.size() > 0) {
                // we can filter these game news by the client-supplied genre argument
                List<GameNews> filteredNews = gamesService.getFilteredList(allNews, filteredGames);
                if (filteredNews.size() > 0) {
                    // if there are any game news matching the applied genre filter, returns the list
                    return new ResponseEntity<>(filteredNews, HttpStatus.OK);
                } else {
                    // otherwise, there were no game news matching the genre filter,therefore returns
                    // a NOT_FOUND response
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        // something went wrong and the API returned a bad response
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/giveaways/filter")
    @ResponseBody
    public ResponseEntity<List<Giveaway>> getFilteredGiveaway(@RequestParam String genre) {
        String gamesUrl = "https://mmo-games.p.rapidapi.com/games?category={genre}";
        // attaches the expected header for the external API endpoint
        HttpEntity entity = gamesService.getHeaderEntity();

        // calls the external API endpoint to get the games filtered by the specified genre
        RestTemplate restTemplateGames = new RestTemplate();
        ResponseEntity<Game[]> responseGamesEndpoint;
        try {
            responseGamesEndpoint = restTemplateGames.exchange(
                gamesUrl, HttpMethod.GET, entity, Game[].class, genre);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Game> filteredGames = null;
        // server responded with OK, therefore returned the list of filtered game objects
        if (responseGamesEndpoint.getStatusCode() == HttpStatus.OK) {
            filteredGames = Arrays.asList(responseGamesEndpoint.getBody());
        } else {
            // user supplied an invalid genre token for filtering or external server is unavailable,
            // therefore returns a BAD_REQUEST response.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String allGiveawaysUrl = "https://mmo-games.p.rapidapi.com/giveaways";

        // calls the external API endpoint to get all game related news in the MMO GAMES database
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Giveaway[]> responseNewsEndpoint = restTemplate.exchange(
            allGiveawaysUrl, HttpMethod.GET, entity, Giveaway[].class);

        // external server responded with OK, therefore all game news were extracted.
        if (responseNewsEndpoint.getStatusCode() == HttpStatus.OK) {
            List<Giveaway> allGiveaways = Arrays.asList(responseNewsEndpoint.getBody());
            // if there are any news provided from the external API
            if (allGiveaways.size() > 0) {
                // we can filter these game news by the client-supplied genre argument
                List<Giveaway> filteredGiveaways = gamesService.getFilteredList(allGiveaways, filteredGames);
                if (filteredGiveaways.size() > 0) {
                    // if there are any game news matching the applied genre filter, returns the list
                    return new ResponseEntity<>(filteredGiveaways, HttpStatus.OK);
                } else {
                    // otherwise, there were no game news matching the genre filter,therefore returns
                    // a NOT_FOUND response
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // something went wrong and the API returned a bad response
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
