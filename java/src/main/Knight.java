package main;

/**
 * The knight object.
 */
public class Knight implements Piece {

	@Override
	public boolean validateMove(String futurePieceType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		boolean isValidMove = false;
		
		// Move up
		if (currentPositionX > futurePositionX) {
			boolean upLeft = currentPositionX - futurePositionX == 2 && currentPositionY - futurePositionY == 1 ? true : false;
			
			boolean leftUp = currentPositionX - futurePositionX == 1 && currentPositionY - futurePositionY == 2 ? true : false;
			
			boolean upRight = currentPositionX - futurePositionX == 2 && futurePositionY - currentPositionY == 1 ? true : false;
			
			boolean rightUp = currentPositionX - futurePositionX == 1 && futurePositionY - currentPositionY == 2 ? true : false;
			
			isValidMove = upLeft || leftUp || upRight || rightUp ? true : false;
		// Move down
		} else if (currentPositionX < futurePositionX) {
			boolean downLeft = futurePositionX - currentPositionX == 2 && currentPositionY - futurePositionY == 1 ? true : false;
			
			boolean leftDown = futurePositionX - currentPositionX == 1 && currentPositionY - futurePositionY == 2 ? true : false;
			
			boolean downRight = futurePositionX - currentPositionX == 2 && futurePositionY - currentPositionY == 1 ? true : false;
			
			boolean rightDown = futurePositionX - currentPositionX == 1 && futurePositionY - currentPositionY == 2 ? true : false;
			
			isValidMove = downLeft || leftDown || downRight || rightDown ? true : false;
		}
		
		return isValidMove;
	}

}
