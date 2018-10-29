package main;

/**
 * The pawn object.
 */
public class Pawn extends Piece {
	
	private String black = Constants.BLACK.getString();
	private String white = Constants.WHITE.getString();
	private int blackMajorRow = Constants.BLACK_MAJOR_ROW.getInt();
	private int whiteMajorRow = Constants.WHITE_MAJOR_ROW.getInt();

	/**
	 * The constructor.
	 * 
	 * @param color The color.
	 * @param type The type.
	 * @param position The position.
	 */
	public Pawn(String color, String type, int[] position) {
		super(color, type, position);
	}
	
	/**
	 * Validates the future move to a blank position
	 * 
	 * @param futureMovePosition The future position
	 */
	public boolean validMove(int[] futureMovePosition) {
		boolean validPieceMove = false;
		int futureX = futureMovePosition[0];
		int currentX = this.getPosition()[0];
		String color = this.getColor();
		
		if(color == black) {
			validPieceMove = futureX > currentX && futureX <= whiteMajorRow ? true : false;
		} else if(color == white) {
			validPieceMove = futureX < currentX && futureX >= blackMajorRow ? true : false;
		}
		
		return validPieceMove;
	}

}
