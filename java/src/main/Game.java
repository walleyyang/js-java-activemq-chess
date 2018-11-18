package main;

import java.util.Arrays;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;
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
		 this.id = 123;
		 
		 String status = convertStatusToJson();
		 
		 ActiveMQ activeMQ = new ActiveMQ();
		 activeMQ.sendMessage(status);
		 
		 Games games = Games.getInstance();
		 games.addGame(this);
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
	public void setId() {
		Random randomId = new Random();
		int gameMinId = Constants.GAME_MIN_ID.getInt();
		int gameMaxId = Constants.GAME_MAX_ID.getInt();
		
		id = randomId.nextInt(gameMaxId - gameMinId) + gameMinId;
	}
	
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
	 * Converts the game status to JSON.
	 * 
	 * @return JSON object
	 */
	public String convertStatusToJson() {
		String json = "";
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(this);
		} catch (Exception e) {
			System.out.println("Convert to JSON Exception: " + e);
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * Validates the move from the player and the positions in the game.
	 * 
	 * @param gameMove The move object
	 */
	public void validateMove(GameMove gameMove) {
		int id = gameMove.getId();
		int[] currentMovePosition = gameMove.getCurrentPosition();
		int[] futureMovePosition = gameMove.getFuturePosition();
		
		String currentPlayerTurnColor = gameMove.getCurrentPlayerTurnColor();
		
		Piece currentPosition = getPiecePosition(currentMovePosition);
		Piece futurePosition = getPiecePosition(futureMovePosition);
		
		String currentPositionColor = null;
		String currentPositionType = null;
		
		String futurePositionColor = null;
		String futurePositionType = null;
		
		if(currentPosition != null) {
			currentPositionColor = currentPosition.getColor();
			currentPositionType = currentPosition.getType();
		}
		
		if(futurePosition != null) {
			futurePositionColor = futurePosition.getColor();
			futurePositionType = futurePosition.getType();
		}
		
		// Validate selected piece belongs to current player
		if(currentPlayerTurnColor == currentPositionColor) {
			boolean validPieceMove = false;
			
			// Validate selected piece can move to future position
			if(futurePosition == null) {
				validPieceMove = validatePieceMove(currentPosition, futureMovePosition);				
			} //else if(futurePositionColor != currentPlayerTurnColor) {
				//validPieceMove = validatePieceMove(currentPosition, futurePosition);
			//}
			
			if(validPieceMove) {
				//
			}
			
			
		} else {
			System.out.println("Validate Move Error.");
		}
		
		
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
	
//	public boolean validatePieceMove(Piece currentPosition, Piece futurePosition) {
//		
//	}
	
	/**
	 * Gets the piece based on the position.
	 * 
	 * @param position The position for the piece
	 * 
	 * @return The found piece or null
	 */
	public Piece getPiecePosition(int[] position) {
		Piece foundPiece = null;
		
		for(Piece piece : this.pieces.getPieces()) {
			if(Arrays.equals(piece.getPosition(), position)) {
				System.out.println(Arrays.equals(piece.getPosition(), position));
				foundPiece = piece;
			}
		}
		
		return foundPiece;
	}
	
	
}
