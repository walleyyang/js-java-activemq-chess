package main;

/**
 * The rook object.
 */
public class Rook extends Piece {

	/**
	 * The constructor.
	 * 
	 * @param color The color.
	 * @param type The type.
	 * @param position The position.
	 */
	public Rook(String color, String type, int[] position) {
		super(color, type, position);
	}
	
	/**
	 * Checks move
	 */
	/*public boolean isValidMove(int[] newPosition) {
		boolean valid = false;
		//int currentPosition[] = this.getPosition();
		//int currentX = currentPosition[0];
		//int currentY = currentPosition[1];
		int newX = newPosition[0];
		int newY = newPosition[1];
		
		//TODO: Check horizontal and vertical for obstructions
		
		return valid;
	}*/

}
