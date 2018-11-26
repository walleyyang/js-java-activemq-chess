package main;

/**
 * The pawn object.
 */
public class Pawn implements Piece {
	
	private int BLACK_MINOR_ROW = Constants.BLACK_MINOR_ROW.getInt();
	private int WHITE_MINOR_ROW = Constants.WHITE_MINOR_ROW.getInt();
	
	@Override
	public boolean validateMove(String futureType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		boolean isValidMove = false;

		if (currentColor.equals(Piece.BLACK)) {
			// Valid initial move
			if ((currentPositionX == BLACK_MINOR_ROW && futurePositionX == BLACK_MINOR_ROW + 2) && 
			    (currentPositionY == futurePositionY) && futureType.isEmpty()) {
				isValidMove = true;
			} else if (!futureType.isEmpty()) {
				// Valid attacking
				if (currentPositionX + 1 == futurePositionX &&
				   (currentPositionY + 1 == futurePositionY ||
				    currentPositionY - 1 == futurePositionY)) {
					isValidMove = true;
				}
			} else if (futureType.isEmpty()) {
				// Valid moves
				if (currentPositionX + 1 == futurePositionX &&
					currentPositionY == futurePositionY) {
					isValidMove = true;
				}
			}
		}
		
		if (currentColor.equals(Piece.WHITE)) {
			if ((currentPositionX == WHITE_MINOR_ROW && futurePositionX == WHITE_MINOR_ROW - 2) && 
			    (currentPositionY == futurePositionY) && futureType.isEmpty()) {
				isValidMove = true;
			} else if (!futureType.isEmpty()) {
				if (currentPositionX - 1 == futurePositionX &&
				   (currentPositionY - 1 == futurePositionY ||
				    currentPositionY + 1 == futurePositionY)) {
					
					isValidMove = true;
				}
			} else if (futureType.isEmpty()) {
				if (currentPositionX - 1 == futurePositionX &&
					currentPositionY == futurePositionY) {
					
					isValidMove = true;
				}
			}	
		}
		
		return isValidMove;
	}
	
}
