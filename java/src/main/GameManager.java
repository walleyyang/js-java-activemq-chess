package main;

import javax.jms.Message;

import com.fasterxml.jackson.databind.ObjectMapper;



/**
* Singleton class to manage game.
*/
public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	private static Game game = new Game();
	private static ActiveMQ activeMQ = new ActiveMQ();

	private GameManager() {
		System.out.println("in game manager");
		
//		ActiveMQ activeMQ = new ActiveMQ();
//		
//		GameMove moveMessage = activeMQ.getMoveMessage();
//		System.out.println(moveMessage);
		PostgreSQLJDBC postgres = new PostgreSQLJDBC();
		postgres.getGameStatus();
		run();
	}
	
	/**
	 * Creates instance of GameManager
	 * 
	 * @return Instance of GameManager
	 */
	public static GameManager getInstance() {
		return gameManager;
	}
	
	/**
	 * Handles the received message
	 * 
	 * @param mappedMessage the consumer message from ActiveMQ
	 */
	public static void handleReceivedMessage(GameMessage mappedMessage) {
		boolean validMove = game.validateMove(mappedMessage);
		System.out.println("in handleReceivedMessage..");
		if (validMove) {	
			mappedMessage.setValidMove(true);
			String validatedMessage = convertStatusToJson(mappedMessage);
			System.out.println(validatedMessage);
			activeMQ.sendMessage(validatedMessage);
		}
	}
	
	/**
	 * Converts the game status to JSON.
	 * 
	 * @param value the Java object
	 * 
	 * @return JSON object
	 */
	public static String convertStatusToJson(Object value) {
		String json = "";
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(value);
		} catch (Exception e) {
			System.out.println("Convert to JSON Exception: " + e);
			e.printStackTrace();
		}
		
		return json;
	}
	
	protected static void run() {
		System.out.println("in run...");
	}

}
