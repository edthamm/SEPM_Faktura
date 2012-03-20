package dao;

import java.sql.Connection;

public interface DatabaseConnector {
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws DatabaseConnectorException if no connection can be established
	 */
	public Connection getConnection() throws DatabaseConnectorException;
}
