package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The game object.
 */
public class Game {
	
	private String rook = Constants.ROOK.getString();
	private String knight = Constants.KNIGHT.getString();
	private String bishop = Constants.BISHOP.getString();
	private String queen = Constants.QUEEN.getString();
	private String king = Constants.KING.getString();
	private String pawn = Constants.PAWN.getString();
	private String currentType = "";
	private String futureType = "";

	private ArrayList<int[]> piecePositions = null;	
	
	/**
	 * Validates the move from the player and the positions in the game.
	 * 
	 * @param gameMove The move object
	 */
	public boolean validateMove(GameMessage mappedMessage) {
		boolean isValidMove = false;
		
		String mappedMessageCurrentPlayerTurnColor = mappedMessage.getCurrentPlayerTurnColor();
		int[] mappedMessageCurrentPosition = mappedMessage.getCurrentPosition();
		int[] mappedMessageFuturePosition = mappedMessage.getFuturePosition();
		
		int messageCurrentPositionX = mappedMessageCurrentPosition[0];
		int messageCurrentPositionY = mappedMessageCurrentPosition[1];
		int messageFuturePositionX = mappedMessageFuturePosition[0];
		int messageFuturePositionY = mappedMessageFuturePosition[1];
		
		GenericPiece currentGenericPiece = getPieceFromDatabase(messageCurrentPositionX, messageCurrentPositionY);
		GenericPiece futureGenericPiece = getPieceFromDatabase(messageFuturePositionX, messageFuturePositionY);
		String currentGenericPieceType = currentGenericPiece.getType();
		String currentGenericPieceColor = currentGenericPiece.getColor();
		String futureGenericPieceType = futureGenericPiece.getType();

		// Cannot take own pieces
		if (validateColors(mappedMessageCurrentPlayerTurnColor, currentGenericPiece.getColor(), futureGenericPiece.getColor())) {
			this.piecePositions = getPositionsFromDatabase();
			this.currentType = currentGenericPieceType;
			this.futureType = futureGenericPieceType;
			
			isValidMove = validatePieceMove(futureGenericPieceType,
							  				currentGenericPieceType, 
											currentGenericPieceColor,
											messageCurrentPositionX, 
											messageCurrentPositionY, 
											messageFuturePositionX, 
											messageFuturePositionY);
		}
		
		return isValidMove;		
	}
	
	/**
	 * Validates the colors for the player, current and future pieces
	 * 
	 * @param currentPlayerTurnColor the current player's color
	 * @param currentPieceColor the current piece's color
	 * @param futurePieceColor the future piece's color
	 * 
	 * @return boolean
	 */
	public boolean validateColors(String currentPlayerTurnColor, String currentPieceColor, String futurePieceColor) {
		return currentPlayerTurnColor.equals(currentPieceColor) && !currentPieceColor.equals(futurePieceColor) ? true : false;
	}

	/**
	 * Gets the piece positions from the database
	 * 
	 * @return The piece positions
	 */
	public ArrayList<int[]> getPositionsFromDatabase() {
		PostgreSQLJDBC postgres = new PostgreSQLJDBC();
		String databaseGameStatus = postgres.getGameStatus();
		ArrayList<int[]> positions = new ArrayList<>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(databaseGameStatus);
			JsonNode piecesNode = root.path("pieces");
			
			for (JsonNode node : piecesNode) {
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
						
						int[] databasePosition = new int[2];
						databasePosition[0] = x;
						databasePosition[1] = y;
						
						positions.add(databasePosition);
					}
				}
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return positions;
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
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return genericPiece;
	}
	
	/**
	 * Validates the current piece position with the future blank position based on current piece type.
	 * 
	 * @param futureType the future piece type
	 * @param currentType the current piece
	 * @param currentColor the current piece color
	 * @param currentPositionX the current position X
	 * @param currentPositionY the current position Y
	 * @param futurePositionX the future position X
	 * @param futurePositionY the future position Y
	 * 
	 * @return True or false if the futureMovePosition is valid
	 */
	public boolean validatePieceMove(String futureType,
									 String currentType, 
									 String currentColor, 
									 int currentPositionX, 
									 int currentPositionY, 
									 int futurePositionX, 
									 int futurePositionY) {
		boolean validPieceMove = false;

		if(currentType.equals(rook)) {
			Rook rookType = new Rook();
			rookType.setPiecePositions(this.piecePositions);
			
			validPieceMove = rookType.validateMove(futureType, currentColor, currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		} else if(currentType.equals(knight)) {
			Knight knightType = new Knight();
			
			validPieceMove = knightType.validateMove(futureType, currentColor, currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		} else if(currentType.equals(bishop)) {
			Bishop bishopType = new Bishop();
			bishopType.setPiecePositions(this.piecePositions);
			
			validPieceMove = bishopType.validateMove(futureType, currentColor, currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		} else if(currentType.equals(queen)) {
			Queen queenType = new Queen();
			queenType.setPiecePositions(this.piecePositions);
			
			validPieceMove = queenType.validateMove(futureType, currentColor, currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		} else if(currentType.equals(king)) {
			King kingType = new King();
			
			validPieceMove = kingType.validateMove(futureType, currentColor, currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		} else if(currentType.equals(pawn)) {
			Pawn pawnType = new Pawn();
			
			validPieceMove = pawnType.validateMove(futureType, currentColor, currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		}
		
		return validPieceMove;
	}
	
	public String getCurrentType() { return this.currentType; }
	public String getFutureType() { return this.futureType; }

}
