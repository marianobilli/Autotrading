package autotrading.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private static Database singleton = null;
	private static final String USERNAME = "autotrading";
	private static final String PASSWORD = "autopassword";
	private static final String CONN_STRING = "jdbc:mysql://localhost/autotrading";
	private Connection connection= null;
	
	
	public static void newSingleton() throws SQLException{
		Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		singleton = new Database(conn);
	}
	
	public static Database getSingleton() {
		return singleton;
	}


	public Database(Connection connection){
		 setConnection(connection);
	}
	
	public boolean closeConnection() throws SQLException{
		if ( this.getConnection() != null) {
			 this.getConnection().close();
			return true;
		}
		else
			return false;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
