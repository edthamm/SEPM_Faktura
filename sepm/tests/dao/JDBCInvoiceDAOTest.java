package dao;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class JDBCInvoiceDAOTest {
    private static JDBCInvoiceDAOImpl dao;
	
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	dao = new JDBCInvoiceDAOImpl(new DatabaseConnectorImpl());
    	dao.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateInvoice() {
		dao.createInvoice("2012-01-09", "14:12:13", "testWaiter");
		
	}

	@Test
	public void testUpdateInvoice() {
		fail("Not yet implemented");
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
