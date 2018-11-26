package main;

/**
 * The piece object.
 */
public interface Piece {
	
	public String BLACK = Constants.BLACK.getString();
	public String WHITE = Constants.WHITE.getString();

	/**
	 * Validates the current move with future move
	 * 
	 * @param futurePieceType the future piece type
	 * @param currentColor the current piece color
	 * @param currentPositionX the current position X
	 * @param currentPositionY the current position Y
	 * @param futurePositionX the future position X
	 * @param futurePositionY the future position Y
	 * 
	 * @return boolean
	 */
	public boolean validateMove(String futurePieceType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY);
	
}
