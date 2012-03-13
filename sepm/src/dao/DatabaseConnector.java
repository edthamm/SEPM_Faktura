package dao;

import java.sql.Connection;

public interface DatabaseConnector {
	public Connection getConnection() throws DatabaseConnectorException;
}
