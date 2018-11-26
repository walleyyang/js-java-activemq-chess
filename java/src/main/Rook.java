package main;

import java.util.ArrayList;

/**
 * The rook object.
 */
public class Rook implements Piece {
	
	private ArrayList<int[]> piecePositions = null;
	
	@Override
	public boolean validateMove(String futurePieceType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		boolean isValidMove = true;
		boolean horizontalMove = currentPositionX == futurePositionX && currentPositionY != futurePositionY ? true : false;
		boolean verticalMove = currentPositionY == futurePositionY && currentPositionX != futurePositionX ? true : false;
		int x = -1;
		int y = -1;
		
		// Move was not horizontal or vertical
		if (!horizontalMove && !verticalMove) {
			isValidMove = false;
			
			return isValidMove;
		}

		for (int[] position : piecePositions) {
			x = position[0];
			y = position[1];
			
			// Checks move for move and blocking pieces
			if (verticalMove && y == currentPositionY && y == futurePositionY) {
				// Move up
				if (currentPositionX > futurePositionX) {
					if (futurePositionX < x && currentPositionX > x) {
						isValidMove = false;
						break;
					}
				// Move down
				} else if (currentPositionX < futurePositionX) {
					if (futurePositionX > x && currentPositionX < x) {
						isValidMove = false;
						break;
					}
				}
			} else if (horizontalMove && x == currentPositionX && x == futurePositionX) {
				// Move left
				if (currentPositionY > futurePositionY) {
					if (futurePositionY < y && currentPositionY > y) {
						isValidMove = false;
						break;
					}
				// Move right
				} else if (currentPositionY < futurePositionY) {
					if (futurePositionY > y && currentPositionY < y) {
						isValidMove = false;
						break;
					}
				}
			}			
		}
		
		return isValidMove;
	}
	
	public void setPiecePositions(ArrayList<int[]> piecePositions) {
		this.piecePositions = piecePositions;
	}

}
