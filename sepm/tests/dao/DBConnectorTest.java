package dao;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLNonTransientConnectionException;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.DatabaseConnector;
import dao.DatabaseConnectorImpl;


public class DBConnectorTest {
	
	private static DatabaseConnector dbc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		PropertyConfigurator.configure("tests/log4jtest.properties");
		dbc = new DatabaseConnectorImpl("tests/dao/testdb.properties");
		
	}

	@Test
	public void testGetConnection() {
		dbc = new DatabaseConnectorImpl("tests/dao/testdb.properties");
		try{
			Connection c = dbc.getConnection();
			assertTrue(c != null);
		}
		catch(Exception e){
			fail("Did not get a connection check if DB is running");
		}
	}
	
	@Test
	public void testThatOnlyOneConnectionIsReturned() throws Exception{
		Connection c1 = dbc.getConnection();
		Connection c2 = dbc.getConnection();
		
		assertTrue(c1.equals(c2));
	}

	@Test(expected = SQLNonTransientConnectionException.class)
	public void someTest() throws Exception{
		Connection c = dbc.getConnection();
		c.close();
		Connection c1 = dbc.getConnection();
		c1.createStatement();
	}
}
