package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DatabaseConnectorImpl implements DatabaseConnector{

	private Connection connection = null;
	private String url = "jdbc:hsqldb:hsql://localhost/production";
	private String user = "sa";
	private String password = "";
	private Logger logger = Logger.getLogger("dao.DatabaseConnectorImpl.class");
			
			

	public Connection getConnection() throws DatabaseConnectorException{
		
		if(connection == null){
			tryToConnect3Times();
		}
		
		return connection;
	}



	/**
	 * @throws DatabaseConnectorException 
	 */
	private void tryToConnect3Times() throws DatabaseConnectorException {
		int i = 0;
		while(i<3){
			try{
				connect();
			}
			catch(SQLException e){
				logger.warn("Failed connection attempt to db");
				i++;
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
	 * @throws DatabaseConnectorException 
	 */
	private void checkIfConnectionIsEstablished() throws DatabaseConnectorException {
		if(connection == null){
			logger.error("Could not establish database connection");
			throw new DatabaseConnectorException("Unable to connect to Database");
		}
		else return;
	}
	
	protected void finalize() throws Throwable{
		try{
			connection.close();
		}
		finally{
			super.finalize();
		}
	}


}
