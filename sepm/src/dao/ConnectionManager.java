package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectionManager implements DatabaseConnector{

	private Connection connection = null;
	private String url = "jdbc:hsqldb:hsql://localhost/production";
	private String user = "sa";
	private String password = "";
	private Logger logger = Logger.getLogger("dao.ConnectionManager.class");
			
			

	public Connection getConnection() throws SQLException {
		
		if(connection == null){
			tryToConnect3Times();
		}
		
		return connection;
	}



	/**
	 * @throws SQLException
	 */
	private void tryToConnect3Times() throws SQLException {
		int i = 0;
		while(i<3){
			try{
				connect();
			}
			catch(SQLException e){
				logger.warn("Failed connection attempt to db");
				continue;
			}
			return;
		}
		checkIfConnectionIsEstablished();//i dont think i need to check but just to be on the safe side
	}



	/**
	 * @throws SQLException
	 */
	private void connect() throws SQLException {
		logger.debug("Connetiong to DB: "+url);
		connection = DriverManager.getConnection(url, user, password);
		logger.debug("Connected.");
	}


	/**
	 * @throws SQLException
	 */
	private void checkIfConnectionIsEstablished() throws SQLException {
		if(connection == null){
			logger.error("Could not establish database connection");
			throw new SQLException("Unable to connect to Database");
		}
		else return;
	}


}
