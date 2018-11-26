package main;

import java.util.ArrayList;

/**
 * The queen object.
 */
public class Queen implements Piece {
	
	private ArrayList<int[]> piecePositions = null;
	private boolean diagonalUpMove = false;
	private boolean diagonalDownMove = false;
	

	@Override
	public boolean validateMove(String futurePieceType, String currentColor, int currentPositionX, int currentPositionY, int futurePositionX, int futurePositionY) {
		boolean isValidMove = true;
		ArrayList<int[]> possibleMoves = getPossibleMoves(currentPositionX, currentPositionY, futurePositionX, futurePositionY);
		
		int x = -1;
		int y = -1;
		int possibleMoveX = -1;
		int possibleMoveY = -1;
		
		// Move was not diagonal
//		if (!diagonalUpMove && !diagonalDownMove) {
//			isValidMove = false;
//			
//			return isValidMove;
//		}

		
		for (int[] position : piecePositions) {
			x = position[0];
			y = position[1];
			
			for (int[] possibleMove : possibleMoves) {
				possibleMoveX = possibleMove[0];
				possibleMoveY = possibleMove[1];
				
				
				
				if (x != currentPositionX && y != currentPositionY && x == possibleMoveX && y == possibleMoveY) {
					System.out.println(x + ", " + possibleMoveX + ", " +  y + ", " + possibleMoveY);
					isValidMove = false;
					break;
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
		boolean horizontalMove = currentPositionX == futurePositionX && currentPositionY != futurePositionY ? true : false;
		boolean verticalMove = currentPositionY == futurePositionY && currentPositionX != futurePositionX ? true : false;
		
		// Move was horizontal or vertical
		if (horizontalMove || verticalMove) {
			System.out.println("horizontal or vertical");
				
			// Move up
			if (currentPositionX > futurePositionX) {
				int verticalUpMovedSpaces = currentPositionX - futurePositionX;
				int verticalUpX = -1;
				
				for (int i = 1; i <= verticalUpMovedSpaces; i++) {
					verticalUpX = futurePositionX + i;
					
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
					verticalDownX = futurePositionX + i;
					
					int[] position = new int[2];
					
					position[0] = verticalDownX;
					position[1] = currentPositionY;
					
					possibleMoves.add(position);
				}
			}
				
			
			
//			int upX = -1;
//			int upYLeft = -1;
//			int upYRight = -1;
//			
//			for (int i = 1; i <= upMovedSpaces; i++) {
//				upX = futurePositionX + i;
//				
//				if (currentPositionY > futurePositionY) {
//					upYLeft = futurePositionY + i;
//					
//					int[] position = new int[2];
//					
//					position[0] = upX;
//					position[1] = upYLeft;
//					
//					possibleMoves.add(position);
//				} else if (currentPositionY < futurePositionY) {
//					upYRight = futurePositionY - i;
//					
//					int[] position = new int[2];
//					position[0] = upX;
//					position[1] = upYRight;
//					
//					possibleMoves.add(position);
//				}				
//			}
			
//			for (int[] position : piecePositions) {
//				x = position[0];
//				y = position[1];
//				
//				// Checks move for move and blocking pieces
//				if (verticalMove && y == currentPositionY && y == futurePositionY) {
//					// Move up
//					if (currentPositionX > futurePositionX) {
//						if (futurePositionX < x && currentPositionX > x) {
//							isValidMove = false;
//							break;
//						}
//					// Move down
//					} else if (currentPositionX < futurePositionX) {
//						if (futurePositionX > x && currentPositionX < x) {
//							isValidMove = false;
//							break;
//						}
//					}
//				} else if (horizontalMove && x == currentPositionX && x == futurePositionX) {
//					// Move left
//					if (currentPositionY > futurePositionY) {
//						if (futurePositionY < y && currentPositionY > y) {
//							isValidMove = false;
//							break;
//						}
//					// Move right
//					} else if (currentPositionY < futurePositionY) {
//						if (futurePositionY > y && currentPositionY < y) {
//							isValidMove = false;
//							break;
//						}
//					}
//				}			
//			}
		}
//		
//		// Check diagonal up from future position
//		if (currentPositionX > futurePositionX) {
//			int upMovedSpaces = currentPositionX - futurePositionX;
//			int upX = -1;
//			int upYLeft = -1;
//			int upYRight = -1;
//			
//			for (int i = 1; i <= upMovedSpaces; i++) {
//				upX = futurePositionX + i;
//				
//				if (currentPositionY > futurePositionY) {
//					upYLeft = futurePositionY + i;
//					
//					int[] position = new int[2];
//					
//					position[0] = upX;
//					position[1] = upYLeft;
//					
//					possibleMoves.add(position);
//				} else if (currentPositionY < futurePositionY) {
//					upYRight = futurePositionY - i;
//					
//					int[] position = new int[2];
//					position[0] = upX;
//					position[1] = upYRight;
//					
//					possibleMoves.add(position);
//				}				
//			}
//			
//			diagonalUpMove = upX == currentPositionX && (upYLeft == currentPositionY || upYRight == currentPositionY) ? true : false;
//		}
//		
//		// Check diagonal down from future position
//		if (currentPositionX < futurePositionX) {
//			int downMovedSpaces = futurePositionX - currentPositionX;
//			int downX = -1;
//			int downYLeft = -1;
//			int downYRight = -1;
//			
//			for (int i = 1; i <= downMovedSpaces; i++) {
//				downX = futurePositionX - i;
//				
//				if (currentPositionY > futurePositionY) {
//					downYLeft = futurePositionY + i;
//					
//					int[] position = new int[2];
//					
//					position[0] = downX;
//					position[1] = downYLeft;
//					
//					possibleMoves.add(position);
//				} else if (currentPositionY < futurePositionY) {
//					downYRight = futurePositionY - i;
//					
//					int[] position = new int[2];
//					position[0] = downX;
//					position[1] = downYRight;
//					
//					possibleMoves.add(position);
//				}	
//			}
//			
//			diagonalDownMove = downX == currentPositionX && (downYLeft == currentPositionY || downYRight == currentPositionY) ? true : false;
//		}
		
		return possibleMoves;
	}

	public void setPiecePositions(ArrayList<int[]> piecePositions) {
		this.piecePositions = piecePositions;
	}
	
}
