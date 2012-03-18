package dao;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entities.Consumption;
import entities.Invoice;
import entities.InvoiceClosedException;


public class JDBCInvoiceDAOTest {
    private static JDBCInvoiceDAOImpl dao;
	private static Connection c;
	private static Statement s;
    private Invoice i;
    
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	PropertyConfigurator.configure("tests/log4jtest.properties");
    	dao = new JDBCInvoiceDAOImpl(new DatabaseConnectorImpl());
		c = dao.getConnection();
    	c.setAutoCommit(false);
		s = c.createStatement();
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
	public void testIfCreateInsertsToDB() throws SQLException, InvoiceDAOException{
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
	public void testUpdateInvoiceWithoutConsumptions() throws InvoiceClosedException, SQLException, InvoiceDAOException {		
		i.setWaiter("update");
		
		dao.updateInvoice(i);
		
		ResultSet r = s.executeQuery("select count(*) as num from invoice where waiter = 'update'");
		r.next();
		assertTrue(r.getInt("num") == 1);
		
	}
	
	@Test
	public void testUpdateInvoiceWithConsumptions() throws SQLException, InvoiceClosedException, InvoiceDAOException{
		s.executeUpdate("insert into products values (7,'beer',1,1,'b',true)");
	
		List<Consumption> consumptions = new LinkedList<Consumption>();
		Consumption beer = new Consumption(7,1, 13.5);
		consumptions.add(beer);
		i.setConsumptions(consumptions);
		i.setSum(13.5);
		
		dao.updateInvoice(i);
		
		ResultSet r = s.executeQuery("select count(*) as num from contains");
		r.next();
		assertTrue(r.getInt("num") == 1);
	}

	@Test
	public void testDeleteInvoice() throws SQLException, JDBCInvoiceDAOImplException {
		ResultSet before = s.executeQuery("select count(*) as num from invoice");
		
		dao.deleteInvoice(i);
		
		ResultSet after = s.executeQuery("select count(*) as num from invoice");
		before.next();
		after.next();
		assertTrue(before.getInt("num")-1 == after.getInt("num"));
	}
	
	@Test
	public void testDeleteInvoiceDoesNotDeleteClosedInvoice() throws SQLException, JDBCInvoiceDAOImplException{
		ResultSet before = s.executeQuery("select count(*) as num from invoice");
		int l = s.executeUpdate("update invoice set total = 1 where waiter = 'testWaiter'");
		if(l != 1){fail("Could not close invoice aborting test");}
		
		dao.deleteInvoice(i);
		
		ResultSet after = s.executeQuery("select count(*) as num from invoice");
		before.next();
		after.next();
		
		assertTrue(before.getInt("num") == after.getInt("num"));
	}

	@Test
	public void testFindAll() throws SQLException, JDBCInvoiceDAOImplException {
		ResultSet size = s.executeQuery("select count(*) as num from invoice");
		
		List<Invoice> result = dao.findAll();
		
		size.next();
		assertTrue(size.getInt("num") == result.size());
	}

	@Test
	public void testFindById() throws JDBCInvoiceDAOImplException {
		Invoice found = dao.findById(i.getId());
		
		assertTrue(found.getId() == i.getId());
	}
	
	@Test
	public void testFindByIdForClosedInvoice() throws SQLException, InvoiceClosedException, JDBCInvoiceDAOImplException{
		//TODO find a better way to test this
		s.executeUpdate("insert into products values (7,'beer',1,1,'b',true)");
		List<Consumption> consumptions = new LinkedList<Consumption>();
		Consumption beer = new Consumption(7,1, 13.5);
		consumptions.add(beer);
		i.setConsumptions(consumptions);
		i.setSum(13.5);
		dao.updateInvoice(i);
		
		Invoice found = dao.findById(i.getId());
		
		assertTrue(found.getConsumptions().listIterator().next().getPrice() == 13.5);
	}

	@Test
	public void testFindByDate() throws SQLException, JDBCInvoiceDAOImplException {
		ResultSet size = s.executeQuery("select count(*) as num from invoice where date = '2012-01-09'");
		
		List<Invoice> result = dao.findByDate("2012-01-09");
		
		size.next();
		assertTrue(result.size() == size.getInt("num"));
	}

}
