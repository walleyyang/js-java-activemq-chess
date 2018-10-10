package main;

import java.util.Random;

/**
 * The game object.
 */
public class Game {
	
	private int id = 0;
	private boolean joinable = true;
	private boolean gameOver = false;
	private Player[] players = new Player[2];
	private String turn = Constants.WHITE.getString();
	private Pieces pieces = new Pieces();
	
	public Game() {
		 setId();
	}
	
	public int getId() { return id; }
	public boolean getJoinable() { return joinable; }
	public boolean getGameOver() { return gameOver; }
	public Player[] getPlayers() { return players; }
	public String getTurn() { return turn; }
	
	/**
	 * Sets a random number to the game id.
	 */
	public void setId() {
		Random randomId = new Random();
		int gameMinId = Constants.GAME_MIN_ID.getInt();
		int gameMaxId = Constants.GAME_MAX_ID.getInt();
		
		id = randomId.nextInt(gameMaxId - gameMinId) + gameMinId;
	}
	
	/**
	 * Sets joinable depending on the number of players.
	 */
	public void setJoinable() {
		int joinableFull = Constants.JOINABLE_FULL.getInt();
		
		joinable = players.length == joinableFull ? false : true;
	}
	
	/**
	 * Sets the player for the game.
	 * 
	 * @param player
	 */	
	public void setPlayers(Player player) {
		boolean joinable = getJoinable();
		
		if(joinable) {
			if(getPlayers()[0] == null) {
				getPlayers()[0] = player;
			} else if(getPlayers()[1] == null) {
				getPlayers()[1] = player;
			}
		}
	}
	
	/**
	 * Sets turn for player based on color.
	 */
	public void setTurn() {
		String black = Constants.BLACK.getString();
		String white = Constants.WHITE.getString();
		
		turn = turn == black ? white : black;
	}
	
	/**
	 * Sets game over.
	 */
	public void setGameOver() {
		gameOver = true;
	}
	
}
