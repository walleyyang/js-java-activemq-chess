package main;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
* Singleton class to manage game.
*/
public class GameManager {
	
	private static GameManager gameManager = new GameManager();
	private static Game game = new Game();
	private static ActiveMQ activeMQ = new ActiveMQ();
	
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
		boolean isValidMove = game.validateMove(mappedMessage);
		
		if (isValidMove) {
			String futureType = game.getFutureType();
			String currentType = game.getCurrentType();
			
			mappedMessage.setValidMove(true);
			
			// Game Over
			if (futureType.equals(Constants.KING.getString())) {
				mappedMessage.setGameOver(true);
			}
			
			// Pawn promotion
			if (currentType.equals(Constants.PAWN.getString())) {
				String color = mappedMessage.getCurrentPlayerTurnColor();
				String WHITE = Constants.WHITE.getString();
				String BLACK = Constants.BLACK.getString();
				
				int x = mappedMessage.getFuturePosition()[0];
				int blackPromotionRow = Constants.WHITE_MAJOR_ROW.getInt();
				int whitePromotionRow = Constants.BLACK_MAJOR_ROW.getInt();
				
				if((color.equals(WHITE) && x == whitePromotionRow) || (color.equals(BLACK) && x == blackPromotionRow)) {
					mappedMessage.setPawnPromotion(true);
				}
			}
		} else {
			mappedMessage.setValidMove(false);
		}
		
		String validatedMessage = convertStatusToJson(mappedMessage);
		//System.out.println(validatedMessage);
		activeMQ.sendMessage(validatedMessage);
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

}
