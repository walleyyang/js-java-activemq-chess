package main;

/**
 * Class for generic piece
 */
public class GenericPiece {
	private String type = "";
	private String color = "";
	private int[] position = null;
	
	public void setType(String type) { this.type = type; }
	public void setColor(String color) { this.color = color; }
	public void setPosition(int[] position) { this.position = position; }
	
	public String getType() { return type; }
	public String getColor() { return color; }
	public int[] getPosition() { return position; }
}
