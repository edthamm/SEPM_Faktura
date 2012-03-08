package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {
	public Connection getConnection() throws SQLException;
}
