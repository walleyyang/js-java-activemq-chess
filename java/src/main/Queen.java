package main;

import java.util.ArrayList;

/**
 * The queen object.
 */
public class Queen implements Piece {
	
	private ArrayList<int[]> piecePositions = null;
	private boolean diagonalUpMove = false;
	private boolean diagonalDownMove = false;
	private boolean horizontalMove = false;
	private boolean verticalMove = false;
	
	@Override
	public boolean validateMove(String futurePieceType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		boolean isValidMove = true;
		ArrayList<int[]> possibleMoves = getPossibleMoves(currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		
		int x = -1;
		int y = -1;
		int possibleMoveX = -1;
		int possibleMoveY = -1;
		
		// Move was not horizontal, vertical or diagonal
		if (!diagonalUpMove && !diagonalDownMove && !verticalMove && !horizontalMove) {
			isValidMove = false;
			
			return isValidMove;
		}
		
		validatingMove: {
			for (int[] position : piecePositions) {
				x = position[0];
				y = position[1];
				
				for (int[] possibleMove : possibleMoves) {
					possibleMoveX = possibleMove[0];
					possibleMoveY = possibleMove[1];
					
					if (diagonalUpMove || diagonalDownMove) {
						if (x == possibleMoveX && y == possibleMoveY && x != futurePositionX && y != futurePositionY) {
							isValidMove = false;
							
							break validatingMove;
						}
					} else if (horizontalMove || verticalMove) {
						if (x == possibleMoveX && y == possibleMoveY) {
							// Can take piece
							if(x == futurePositionX && y == futurePositionY) {
								isValidMove = true;
								
								break validatingMove;
							} else {
								isValidMove = false;
								
								break validatingMove;
							}
						}
					}
					
				}
			}	
		}

		return isValidMove;
	}
	
	/**
	 * Gets a list of positions for possible moves from futurePosition
	 * 
	 * @param currentPositionX
	 * @param currentPositionY
	 * @param futurePositionX
	 * @param futurePositionY
	 * 
	 * @return possibleMoves
	 */
	public ArrayList<int[]> getPossibleMoves(int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		ArrayList<int[]> possibleMoves = new ArrayList<>();
		horizontalMove = currentPositionX == futurePositionX && currentPositionY != futurePositionY ? true : false;
		verticalMove = currentPositionY == futurePositionY && currentPositionX != futurePositionX ? true : false;
		
		// Move was horizontal or vertical
		if (horizontalMove || verticalMove) {				
			// Move up
			if (currentPositionX > futurePositionX) {
				int verticalUpMovedSpaces = currentPositionX - futurePositionX;
				int verticalUpX = -1;
				
				for (int i = 1; i <= verticalUpMovedSpaces; i++) {
					verticalUpX = currentPositionX - i;
					
					int[] position = new int[2];
					
					position[0] = verticalUpX;
					position[1] = currentPositionY;
					possibleMoves.add(position);
				}
			// Move down
			} else if (currentPositionX < futurePositionX) {
				int verticalDownMovedSpaces = futurePositionX - currentPositionX;
				int verticalDownX = -1;
				
				for (int i = 1; i <= verticalDownMovedSpaces; i++) {
					verticalDownX = currentPositionX + i;
					
					int[] position = new int[2];
					
					position[0] = verticalDownX;
					position[1] = currentPositionY;
					possibleMoves.add(position);
				}
			// Move left
			} else if (currentPositionY > futurePositionY) {
				int horizontalLeftMovedSpaces = currentPositionY - futurePositionY;
				int horizontalLeftY = -1;
				
				for (int i = 1; i <= horizontalLeftMovedSpaces; i++) {
					horizontalLeftY = currentPositionY - i;
					
					int[] position = new int[2];
					
					position[0] = currentPositionX;
					position[1] = horizontalLeftY;
					possibleMoves.add(position);
				}
			// Move right	
			} else if (currentPositionY < futurePositionY) {
				int horizontalRightMovedSpaces = futurePositionY - currentPositionY;
				int horizontalRightY = -1;
				
				for (int i = 1; i <= horizontalRightMovedSpaces; i++) {
					horizontalRightY = currentPositionY + i;
					
					int[] position = new int[2];
					
					position[0] = currentPositionX;
					position[1] = horizontalRightY;
					possibleMoves.add(position);
				}
			}
		// Move is diagonal
		} else {
			// Check diagonal up from future position
			if (currentPositionX > futurePositionX) {
				int upMovedSpaces = currentPositionX - futurePositionX;
				int upX = -1;
				int upYLeft = -1;
				int upYRight = -1;
				
				for (int i = 1; i <= upMovedSpaces; i++) {
					upX = futurePositionX + i;
					
					if (currentPositionY > futurePositionY) {
						upYLeft = futurePositionY + i;
						
						int[] position = new int[2];
						
						position[0] = upX;
						position[1] = upYLeft;
						possibleMoves.add(position);
						
						// Diagonal not adding future position
						if (i == upMovedSpaces) {
							position[0] = futurePositionX;
							position[1] = futurePositionY;
							possibleMoves.add(position);
						}
					} else if (currentPositionY < futurePositionY) {
						upYRight = futurePositionY - i;
						
						int[] position = new int[2];
						position[0] = upX;
						position[1] = upYRight;
						possibleMoves.add(position);
						
						if (i == upMovedSpaces) {
							position[0] = futurePositionX;
							position[1] = futurePositionY;
							possibleMoves.add(position);
						}
					}				
				}
				
				diagonalUpMove = upX == currentPositionX && (upYLeft == currentPositionY || upYRight == currentPositionY) ? true : false;
			}
			
			// Check diagonal down from future position
			if (currentPositionX < futurePositionX) {
				int downMovedSpaces = futurePositionX - currentPositionX;
				int downX = -1;
				int downYLeft = -1;
				int downYRight = -1;
				
				for (int i = 1; i <= downMovedSpaces; i++) {
					downX = futurePositionX - i;
					
					if (currentPositionY > futurePositionY) {
						downYLeft = futurePositionY + i;
						
						int[] position = new int[2];
						
						position[0] = downX;
						position[1] = downYLeft;
						
						possibleMoves.add(position);
						
						if (i == downMovedSpaces) {
							position[0] = futurePositionX;
							position[1] = futurePositionY;
							possibleMoves.add(position);
						}
					} else if (currentPositionY < futurePositionY) {
						downYRight = futurePositionY - i;
						
						int[] position = new int[2];
						position[0] = downX;
						position[1] = downYRight;
						possibleMoves.add(position);
						
						if (i == downMovedSpaces) {
							position[0] = futurePositionX;
							position[1] = futurePositionY;
							possibleMoves.add(position);
						}
					}	
				}
				
				diagonalDownMove = downX == currentPositionX && (downYLeft == currentPositionY || downYRight == currentPositionY) ? true : false;
			}			
		}

		return possibleMoves;
	}

	public void setPiecePositions(ArrayList<int[]> piecePositions) {
		this.piecePositions = piecePositions;
	}
	
}
