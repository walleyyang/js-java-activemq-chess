package main;

/**
 * The king object.
 */
public class King implements Piece {

	@Override
	public boolean validateMove(String futurePieceType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		boolean isValidMove = false;
		
		// Move up
		if (currentPositionX > futurePositionX) {
			boolean verticalUp = currentPositionX - futurePositionX == 1 && currentPositionY == futurePositionY ? true : false;
			
			boolean diagonalUpLeft = currentPositionX - futurePositionX == 1 && 
					currentPositionY - futurePositionY == 1 ? true : false;
			
			boolean diagonalUpRight = currentPositionX - futurePositionX == 1 && 
					futurePositionY - currentPositionY == 1 ? true : false;
			
			isValidMove = verticalUp || diagonalUpLeft || diagonalUpRight ? true : false;
		// Move down
		} else if (currentPositionX < futurePositionX) {
			boolean verticalDown = futurePositionX - currentPositionX == 1 && currentPositionY == futurePositionY ? true : false;
			
			boolean diagonalDownLeft = futurePositionX - currentPositionX == 1 && 
					currentPositionY - futurePositionY == 1 ? true : false;
			
			boolean diagonalDownRight = futurePositionX - currentPositionX == 1 && 
					futurePositionY - currentPositionY == 1 ? true : false;
			
			isValidMove = verticalDown | diagonalDownLeft | diagonalDownRight ? true : false;
		// Move left
		} else if (currentPositionY > futurePositionY) {
			isValidMove = currentPositionY - futurePositionY == 1 ? true : false;
		// Move right
		} else if (currentPositionY < futurePositionY) {
			isValidMove = futurePositionY - currentPositionY == 1 ? true : false;
		}
		
		return isValidMove;
	}

}
