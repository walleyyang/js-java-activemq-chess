package main;

/**
 * The constants.
 */
public enum Constants {
	
	BISHOP("Bishop"),
	BLACK("Black"),
	BLACK_MAJOR_ROW(0),
	BLACK_MINIOR_ROW(1),
	GAME_MAX_ID(1000000),
	GAME_MIN_ID(1),
	JOINABLE_FULL(2),
	KING("King"),
	KNIGHT("Knight"),
	PAWN("Pawn"),
	QUEEN("Queen"),
	ROOK("Rook"),
	TOTAL_PIECES(32),
	WHITE("White"),
	WHITE_MAJOR_ROW(7),
	WHITE_MINOR_ROW(6);
	
	private int intValue;
	private String stringValue;
	
	/**
	 * Set Constants int value.
	 */
	private Constants(int value) {
		this.intValue = value;
	}
	
	/**
	 * Set Constants string value.
	 */
	private Constants(String value) {
		this.stringValue = value;
	}
	
	/**
	 * Get Constants int value.
	 */
	public int getInt() {
		return intValue;
	}
	
	/**
	 * Get Constants string value.
	 */
	public String getString() {
		return stringValue;
	}
		
}
