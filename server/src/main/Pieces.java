package main;

/**
 * The pieces object.
 */
public class Pieces {
	
	private Piece[] pieces = new Piece[Constants.TOTAL_PIECES.getInt()];
	
	public Pieces() {
		
	}
	
	/**
	 * Sets the pieces.
	 * 
	 * @param index The pieces array index.
	 * @param piece The piece for the array index.
	 */
	public void setPieces(int index, Piece piece) {
		pieces[index] = piece;
	}
	
}
