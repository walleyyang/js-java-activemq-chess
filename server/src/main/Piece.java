package main;

/**
 * The piece object.
 */
public class Piece {

	private String color = "";
	private String type = "";
	private int[] position = new int[2];
	private boolean removed = false;
	
	/**
	 * The constructor.
	 * 
	 * @param color The piece color.
	 * @param type The piece type.
	 * @param position The piece position.
	 */
	public Piece(String color, String type, int[] position) {
		this.color = color;
		this.type = type;
		this.position = position;
	}
	
	public String getColor() { return color; }
	public String getType() { return type; }
	public int[] getPosition() { return position; }
	public boolean getRemoved() { return removed; }
	
	/**
	 * Sets removed.
	 * 
	 * @param value The value to set removed.
	 */
	public void setRemoved(boolean value) {
		removed = value;
	}
	
}
