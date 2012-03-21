package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The Class DatabaseConnectorImpl.
 */
public class DatabaseConnectorImpl implements DatabaseConnector{

	private Connection connection = null;
	private String url = "jdbc:hsqldb:hsql://localhost/testdb";
	private String user = "sa";
	private String password = "";
	private Logger logger = Logger.getLogger("dao.DatabaseConnectorImpl.class");
	
	/**
	 * Instantiates a new database connector.
	 *
	 * @param pathToPropertiesFile the path to properties file
	 */
	public DatabaseConnectorImpl(String pathToPropertiesFile){
		configureWithFile(pathToPropertiesFile);
	}
					
	private void configureWithFile(String pathToPropertiesFile) {
		try {
			logger.debug("Start configutation");
			Properties properties = loadProperties(pathToPropertiesFile);
			setValues(properties);
		} catch (FileNotFoundException e) {
			logger.warn("Could not open properties file falling back to defaults");
			return;
		} catch (IOException e) {
			logger.warn("Could not read from properties file falling back to defaults");
			return;
		}
		
	}

	private Properties loadProperties(String pathToPropertiesFile)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		FileInputStream inStream = new FileInputStream(pathToPropertiesFile);
		properties.load(inStream);
		return properties;
	}

	private void setValues(Properties properties) {
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
	}

	
	public Connection getConnection() throws DatabaseConnectorException{
		
		
		if(connection == null){
			tryToConnect3Times();
		}
		
		return connection;
	}

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
			} catch (ClassNotFoundException e) {
				logger.error("Could not load DatabaseDriver");
				throw new DatabaseConnectorException("Could not load DatabaseDriver");
			}
			return;
		}
		checkIfConnectionIsEstablished();//i dont think i need to check but just to be on the safe side
	}

	private void connect() throws SQLException, ClassNotFoundException {
		logger.debug("Connetiong to DB: "+url);
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		connection = DriverManager.getConnection(url, user, password);
		logger.debug("Connected.");
	}

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
