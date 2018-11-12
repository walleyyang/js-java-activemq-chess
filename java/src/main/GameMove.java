package main;

/**
 * Used to hold receiving JSON string.
 */
public class GameMove {
	
	private int id = 0;
	private String currentPlayerTurnColor = "";
	private int[] currentPosition = new int[2];
	private int[] futurePosition = new int[2];
	
	public void setId(int id) { this.id = id; }
	
	public void setCurrentPlayerTurnColor(String color) { this.currentPlayerTurnColor = color; }
	
	public void setCurrentPosition(int[] currentPosition) {
		this.currentPosition[0] = currentPosition[0];
		this.currentPosition[1] = currentPosition[1];
	}
	
	public void setFuturePosition(int[] futurePosition) {
		this.futurePosition[0] = futurePosition[0];
		this.futurePosition[1] = futurePosition[1];
	}
	
	public int getId() { return this.id; }
	
	public String getCurrentPlayerTurnColor() { return this.currentPlayerTurnColor; }
	
	public int[] getCurrentPosition() { return this.currentPosition; }
	
	public int[] getFuturePosition() { return this.futurePosition; }
}
