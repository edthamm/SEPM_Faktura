package dao;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entities.Product;
import entities.ProductImpl;


public class JDBCProductDAOTest {
    private static JDBCProductDAOImpl dao;
	private static Connection c;
	private static Statement s;
	private Product p;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	  	PropertyConfigurator.configure("tests/log4jtest.properties");
    	dao = new JDBCProductDAOImpl(new DatabaseConnectorImpl("tests/dao/testdb.properties"));
		c = dao.getConnection();
    	c.setAutoCommit(false);
		s = c.createStatement();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		p = dao.createProduct("beer", 1, 2.8, "wb");
	}

	@After
	public void tearDown() throws Exception {
		dao.getConnection().rollback();
	}

	@Test
	public void testCreateProduct() {
		assertTrue(p != null);
	}
	
	@Test
	public void testppriceafterCreate(){
		assertTrue(p.getPurchasePrice() == 1);
	}
	
	@Test
	public void testrpriceafterCreate(){
		assertTrue(p.getRetailPrice() == 2.8);
	}
	
	@Test
	public void testlableafterCreate(){
		assertTrue(p.getLabel().contains("beer"));
	}
	
	@Test
	public void testsupplierafterCreate(){
		assertTrue(p.getSupplier().contains("wb"));
	}
	
	@Test
	public void testIfCreateInsertsIntoDB() throws SQLException, JDBCProductDAOImplException{
		ResultSet r = s.executeQuery("SELECT COUNT(*) AS num FROM products");
		r.next();
		int initial = r.getInt("num");
		
		dao.createProduct("saft", 2, 3.7, "holler");
		
		r= s.executeQuery("SELECT COUNT(*) AS num FROM products");
		r.next();
		int after = r.getInt("num");
		assertTrue(after == initial+1);
	}

	@Test
	public void testUpdateProduct() throws SQLException, JDBCProductDAOImplException {
		p.setSupplier("update");
		
		dao.updateProduct(p);
		
		ResultSet r = s.executeQuery("select count(*) as num from products where supplier = 'update'");
		r.next();
		assertTrue(r.getInt("num") == 1);
	}
	
	@Test
	public void testUpdateProductFail() throws SQLException, JDBCProductDAOImplException {
		p.setSupplier("update");
		ProductImpl pi = (ProductImpl) p;
		pi.setId(3);
		
		
		dao.updateProduct(pi);
		
		ResultSet r = s.executeQuery("select count(*) as num from products where supplier = 'update'");
		r.next();
		assertTrue(r.getInt("num") == 0);
	}

	@Test
	public void testDeleteProduct() throws SQLException, JDBCProductDAOImplException {
		dao.deleteProduct(p);
		
		ResultSet r = s.executeQuery("select insale from products where pid = "+p.getId());
		r.next();
		assertFalse(r.getBoolean("insale"));
	}
	
	@Test
	public void testDeleteProductFail() throws SQLException, JDBCProductDAOImplException {
		ProductImpl pi = (ProductImpl) p;
		int oldId = pi.getId();
		pi.setId(3);
		dao.deleteProduct(pi);
		
		ResultSet r = s.executeQuery("select insale from products where pid = "+oldId);
		r.next();
		assertTrue(r.getBoolean("insale"));
	}

	@Test
	public void testFindAll() throws JDBCProductDAOImplException {
		List<Product> found = dao.findAll();
		
		assertTrue(found.listIterator().next().getId() == p.getId());
	}

	@Test
	public void testFindById() throws JDBCProductDAOImplException {
		Product found = dao.findById(p.getId());
		
		assertTrue(found.getPurchasePrice() == p.getPurchasePrice());
	}
	
	@Test(expected = JDBCProductDAOImplException.class)
	public void testFindByIdFail() throws JDBCProductDAOImplException {
		dao.findById(7);
	}

	@Test
	public void testFindByName() throws JDBCProductDAOImplException {
		List<Product> found = dao.findByName(p.getLabel());
		
		assertTrue(found.listIterator().next().getId() == p.getId());
	}

	@Test
	public void testFindByNameFail() throws JDBCProductDAOImplException {
		List<Product> found = dao.findByName("r");
		
		assertTrue(found.isEmpty());

	}

}
