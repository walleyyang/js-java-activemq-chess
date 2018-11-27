package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Used for accessing PostgreSQL database
 */
public class PostgreSQLJDBC {
	private Connection connection = null;
	
	public PostgreSQLJDBC() {
		connect();
	}
	
	/**
	 * Connects to PostgreSQL database
	 */
	public void connect() {
		try {
			// Probably should put this in a config file if you plan on using this outside of a demo
			String url = "jdbc:postgresql://localhost:5432/game";
			String user = "postgres";
			String password = "password";
			
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);	
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Gets the game status
	 * 
	 * @return JSON string
	 */
	public String getGameStatus() {
		String gameStatus = "";
		
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM active_game");
			
			while(resultSet.next()) {
				gameStatus = resultSet.getString("game_status");
			}
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
		return gameStatus;
	}
}
