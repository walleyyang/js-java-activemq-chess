package main;

import java.util.ArrayList;

/**
 * Singleton class to hold all games.
 */
public class Games {
	
	private static Games instance = null;
	private static ArrayList<Game> currentGames = null;
	
	private Games() {
		currentGames = new ArrayList<Game>(0);
	}
	
	/**
	 * Creates instance of Games.
	 * 
	 * @return Instance of Games
	 */
	public static Games getInstance() {
		if(instance == null) {
			instance = new Games();
		}
		
		return instance;
	}
	
	/**
	 * Adds new game to the currentGames list.
	 * @param game The Game object
	 */
	public void addGame(Game game) {
		currentGames.add(game);
		
	}

	public static ArrayList<Game> getCurrentGames() { return currentGames; }

}
