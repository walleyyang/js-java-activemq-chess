package main;

/**
 * The player object.
 */
public class Player {
	
	private String color = "";
	private String name = "";
	
	/**
	 * The constructor.
	 * 
	 * @param color The player color.
	 * @param name The player name.
	 */
	public Player(String color, String name) {
		this.color = color;
		this.name = name;
	}
	
	public String getColor() { return color; }
	public String getName() { return name; }
	
}
