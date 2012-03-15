package dao;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entities.Invoice;
import entities.InvoiceClosedException;


public class JDBCInvoiceDAOTest {
    private static JDBCInvoiceDAOImpl dao;
    private Invoice i;
    
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	PropertyConfigurator.configure("tests/log4jtest.properties");
    	dao = new JDBCInvoiceDAOImpl(new DatabaseConnectorImpl());
    	dao.getConnection().setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		i = dao.createInvoice("2012-01-09", "14:12:13", "testWaiter");
	}

	@After
	public void tearDown() throws Exception {
		dao.getConnection().rollback();
	}

	@Test
	public void testCreateInvoice() {
		assertTrue(i != null);
	}
	
	@Test
	public void testDateAfterCreate(){
		assertTrue(i.getDate().toString().equals("2012-01-09"));
	}
	
	@Test
	public void testIdAfterCreate(){
		assertTrue(i.getId() != 0);
	}
	
	@Test
	public void testIfCreateInsertsToDB() throws SQLException, JDBCInvoiceDAOImplException{
		Connection c = dao.getConnection();
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery("SELECT COUNT(*) AS num FROM invoice");
		r.next();
		int initial = r.getInt("num");
		
		dao.createInvoice("2011-01-01", "00:00:01", "tw1");
		
		r= s.executeQuery("SELECT COUNT(*) AS num FROM invoice");
		r.next();
		int after = r.getInt("num");
		assertTrue(after == initial+1);
		
	}

	@Test
	public void testUpdateInvoiceWithoutConsumptions() throws InvoiceClosedException, SQLException {
		Connection c = dao.getConnection();
		Statement s = c.createStatement();		
		i.setWaiter("update");
		
		dao.updateInvoice(i);
		
		ResultSet r = s.executeQuery("select count(*) as num from invoice where waiter = 'update'");
		r.next();
		assertTrue(r.getInt("num") == 1);
		
	}
	
	@Test
	public void testUpdateInvoiceWithConsumptions(){
		fail("not yet done");
	}

	@Test
	public void testDeleteInvoice() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByDate() {
		fail("Not yet implemented");
	}

}
