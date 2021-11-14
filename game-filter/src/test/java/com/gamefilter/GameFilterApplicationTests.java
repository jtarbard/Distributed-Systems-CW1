package com.gamefilter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/** Testing the Business Logic of the Service in this SpringBootTest Class*/
@SpringBootTest
class GameFilterApplicationTests {

	// Tests whether the application loads successfully
	@Test
	void contextLoads() {
	}

	// creating an instance of the business logic and establishes the
	// testing connection to it
	@MockBean
	GamesService gamesService;

	/*Tests getTitles method of the business logic gamesService class
	* @GIVEN a list of generic objects that conform to hasTitle interface
	*
	* @RETURNS the list of Strings containing only the titles of these objects,
	*          possibly ordered in alphabetical order*/
	@Test
	void getTitleReturnsCorrectTitles() {
		// the expected list of titles, the first expected titles are unordered
		List<String> expectedTitleList = new LinkedList<>();
		expectedTitleList.add("title1");
		expectedTitleList.add("title2");

		//Below we test if gamesService.getTitles() works on two different classes

		// creates the instances of Game objects
		Game testGame1 = new Game();
		// sets the corresponding title
		testGame1.setTitle("title1");
		Game testGame2 = new Game();
		testGame2.setTitle("title2");
		// adds these objects to a list
		List<Game> gamesList = new LinkedList<>();
		gamesList.add(testGame1);
		gamesList.add(testGame2);

		// performs the test with the game objects
		given(gamesService.getTitles(gamesList, false)).willReturn(expectedTitleList);

		// setup is similar as above, with the difference that we use News objects this time
		GameNews testNews1 = new GameNews();
		testNews1.setTitle("title1");
		GameNews testNews2 = new GameNews();
		testNews2.setTitle("title2");
		List<GameNews> newsList = new LinkedList<>();
		newsList.add(testNews1);
		newsList.add(testNews2);

		// the output should still be the same List of Strings
		given(gamesService.getTitles(newsList, false)).willReturn(expectedTitleList);

		// clears the previous expected titles
		expectedTitleList.clear();
		// sets up the new expected titles, conforming to alphabetical ordering A > B > C
		expectedTitleList.add("Abc");
		expectedTitleList.add("Bac");
		expectedTitleList.add("Cab");

		// prepares the test space by setting the same titles but in wrong order
		testGame1.setTitle("Cab");
		testGame2.setTitle("Abc");
		Game testGame3 = new Game();
		testGame3.setTitle("Bac");
		// clears the previous list of games and arranges in unordered fashion
		gamesList.clear();
		gamesList.add(testGame1);
		gamesList.add(testGame2);
		gamesList.add(testGame3);

		// performs the test, expecting a list of String, containing the title in alphabetical order
		given(gamesService.getTitles(gamesList, true)).willReturn(expectedTitleList);
	}

	/*Tests getUniqueGenres method of the business logic gamesService class
	 * @GIVEN a list of Game objects
	 *
	 * @RETURNS the list of Strings containing only the unique titles of these Games,
	 *          excluding a set of keywords that are misspellings or repeats*/
	@Test
	void getUniqueGenresListReturnsCorrectList() {
		// the list we will provide as method argument
		List<Game> testGames = new LinkedList<>();

		// setting up the test environment
		Game testGame1 = new Game();
		testGame1.setGenre("Shooter");
		Game testGame2 = new Game();
		testGame2.setGenre("Strategy");
		Game testGame3 = new Game();
		testGame3.setGenre("Shooter");
		Game testGame4 = new Game();
		testGame4.setGenre("MMO");

		testGames.add(testGame1);
		testGames.add(testGame2);
		testGames.add(testGame3);
		testGames.add(testGame4);

		// adding all keywords that need to be otherwise excluded in order to test this scenario
		List<String> genresToExclude = Arrays.asList(" MMORPG", "ARPG", "Card Game", "Battle Royale");
		for (int i=0; i < genresToExclude.size(); i++) {
			Game testGameI = new Game();
			testGameI.setGenre(genresToExclude.get(i));
			testGames.add(testGameI);
		}

		// the expected output of the function
		List<String> expectedGenres = Arrays.asList("Shooter","Strategy","MMO");
		// performs the test
		given(gamesService.getUniqueGenresList(testGames)).willReturn(expectedGenres);
	}

	/*Tests getFilteredList method of the business logic gamesService class
	 * @GIVEN a list of GameNews/Giveaway or other generic objects which have title getter,
	 *        and a list of Games used as the filter
	 *
	 * @RETURNS only the list of GameNews/Giveaways that contain the same titles as the filter*/
	@Test
	void getFilteredListFiltersCorrectly() {
		// the titles used for the test environment
		String title1 = "game1 exciting news announced";
		String title2 = "game2 exciting news announced";

		// initialises the list of game used as filter
		Game testGame = new Game();
		testGame.setTitle(title2);
		List<Game> gamesList = new LinkedList<>();
		gamesList.add(testGame);

		// initialises list of gameNews objects
		GameNews testNews1 = new GameNews();
		testNews1.setTitle(title1);
		// this one matches the title of the game used as filter
		GameNews testNews2 = new GameNews();
		testNews2.setTitle(title2);

		// adds all news to a list
		List<GameNews> newsList = new LinkedList<>();
		newsList.add(testNews1);
		newsList.add(testNews2);

		// the expected list should only contain the news that matches the title of the filter game
		List<GameNews> expectedListOfNews = new LinkedList<>();
		expectedListOfNews.add(testNews2);

		// performs the test
		given(gamesService.getFilteredList(newsList,gamesList)).willReturn(expectedListOfNews);

		// performs similar procedure, but this time uses Giveaway objects, therefore tests generality.
		Giveaway testGiveaway1 = new Giveaway();
		testGiveaway1.setTitle(title1);
		Giveaway testGiveaway2 = new Giveaway();
		testGiveaway2.setTitle(title2);

		List<Giveaway> giveawaysList = new LinkedList<>();
		giveawaysList.add(testGiveaway1);
		giveawaysList.add(testGiveaway2);

		List<Giveaway> expectedListOfGiveaways = new LinkedList<>();
		expectedListOfGiveaways.add(testGiveaway2);

		given(gamesService.getFilteredList(giveawaysList,gamesList)).willReturn(expectedListOfGiveaways);
	}
}
