package main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.jms.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The game object.
 */
public class Game {
	
	private int id = 0;
//	private boolean joinable = true;
	private boolean gameOver = false;
//	private Player[] players = new Player[2];
	private String turn = Constants.WHITE.getString();
	private Pieces pieces = new Pieces();
	
	private String rook = Constants.ROOK.getString();
	private String knight = Constants.KNIGHT.getString();
	private String bishop = Constants.BISHOP.getString();
	private String queen = Constants.QUEEN.getString();
	private String king = Constants.KING.getString();
	private String pawn = Constants.PAWN.getString();
	private String black = Constants.BLACK.getString();
	private String white = Constants.WHITE.getString();
	
	public Game() {
		// TODO: Update 
		//setId();
		// this.id = 123;
		 
		 //String status = convertStatusToJson();
		 
		 //ActiveMQ activeMQ = new ActiveMQ();
//		 activeMQ.sendMessage(status);
		 
//		 Games games = Games.getInstance();
//		 games.addGame(this);
	}
	
	public int getId() { return id; }
//	public boolean getJoinable() { return joinable; }
	public boolean getGameOver() { return gameOver; }
//	public Player[] getPlayers() { return players; }
	public String getTurn() { return turn; }
	public Pieces getPieces() { return pieces; }
	/**
	 * Sets a random number to the game id.
	 */
//	public void setId() {
//		Random randomId = new Random();
//		int gameMinId = Constants.GAME_MIN_ID.getInt();
//		int gameMaxId = Constants.GAME_MAX_ID.getInt();
//		
//		id = randomId.nextInt(gameMaxId - gameMinId) + gameMinId;
//	}
	
	/**
	 * Sets joinable depending on the number of players.
	 */
//	public void setJoinable() {
//		int joinableFull = Constants.JOINABLE_FULL.getInt();
//		
//		joinable = players.length == joinableFull ? false : true;
//	}
	
	/**
	 * Sets the player for the game.
	 * 
	 * @param player
	 */	
//	public void setPlayers(Player player) {
//		boolean joinable = getJoinable();
//		
//		if(joinable) {
//			if(getPlayers()[0] == null) {
//				getPlayers()[0] = player;
//			} else if(getPlayers()[1] == null) {
//				getPlayers()[1] = player;
//			}
//		}
//	}
	
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
	

	
	/**
	 * Validates the move from the player and the positions in the game.
	 * 
	 * @param gameMove The move object
	 */
	public boolean validateMove(GameMessage mappedMessage) {
		boolean isValidMove = false;
		
		int[] mappedMessageCurrentPosition = mappedMessage.getCurrentPosition();
		int[] mappedMessageFuturePosition = mappedMessage.getFuturePosition();
		
		int messageCurrentPositionX = mappedMessageCurrentPosition[0];
		int messageCurrentPositionY = mappedMessageCurrentPosition[1];
		int messageFuturePositionX = mappedMessageFuturePosition[0];
		int messageFuturePositionY = mappedMessageFuturePosition[1];
		
		GenericPiece currentGenericPiece = getPieceFromDatabase(messageCurrentPositionX, messageCurrentPositionY);
		GenericPiece futureGenericPiece = getPieceFromDatabase(messageFuturePositionX, messageFuturePositionY);


		// Cannot take own pieces
		if (!validateColors(currentGenericPiece.getColor(), futureGenericPiece.getColor())) {
			isValidMove = true;
		}
		
		return isValidMove;		
	}
	
	/**
	 * Validates the colors for the current and future pieces
	 * 
	 * @param currentPieceColor the current piece's color
	 * @param futurePieceColor the future piece's color
	 * 
	 * @return boolean
	 */
	public boolean validateColors(String currentPieceColor, String futurePieceColor) {
		return currentPieceColor.equals(futurePieceColor) ? true : false;
	}

	/**
	 * Gets the piece from the database based on the position 
	 * 
	 * @param messagePositionX the message position x
	 * @param messagePositionY the message position y
	 * 
	 * @return the generic piece or null
	 */
	public GenericPiece getPieceFromDatabase(int messagePositionX, int messagePositionY) {
		GenericPiece genericPiece = new GenericPiece();
		
		PostgreSQLJDBC postgres = new PostgreSQLJDBC();
		String databaseGameStatus = postgres.getGameStatus();
		
		try {			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(databaseGameStatus);
			JsonNode piecesNode = root.path("pieces");
			
			for (JsonNode node : piecesNode) {
				String color = node.path("color").asText();
				String type = node.path("type").asText();

				int x = -1;
				int y = -1;
				
				Iterator<JsonNode> position = node.path("position").iterator();
				
				while (position.hasNext()) {
					
					JsonNode item = position.next();
					
					if (position.hasNext()) {
						// Array index 0
						x = item.asInt();
					} else if (!position.hasNext()) {
						// Array index 1
						y = item.asInt();
						
						// The position exists for a piece in the database
						if (messagePositionX == x && messagePositionY == y) {
							int[] databasePosition = new int[2];
							databasePosition[0] = x;
							databasePosition[1] = y;
							
							genericPiece.setColor(color);
							genericPiece.setType(type);
							genericPiece.setPosition(databasePosition);
						}
					}
					
				}
				
			}
		}catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return genericPiece;
	}
	
	/**
	 * Validates the current piece position with the future blank position.
	 * 
	 * @param currentPosition The current piece position
	 * @param futureMovePosition The future position for the current piece position
	 * 
	 * @return True or false if the futureMovePosition is valid
	 */
	public boolean validatePieceMove(Piece currentPosition, int[] futureMovePosition) {
		boolean validPieceMove = false;
		String currentPositionType = currentPosition.getType();
		
		// Check the currentPostion type to see if futureMovePosition is valid
		// TODO: Implement
		if(currentPositionType == rook) {
			validPieceMove = ((Pawn)currentPosition).validMove(futureMovePosition);
		} 
		//else if(currentPositionType == knight) {
//			
//		} else if(currentPositionType == bishop) {
//			
//		} else if(currentPositionType == queen) {
//			
//		} else if(currentPositionType == king) {
//			
//		} else if(currentPositionType == pawn) {
//			
//		}
				
		return validPieceMove;
	}

}
