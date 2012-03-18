package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import entities.Product;
import entities.ProductImpl;

public class JDBCProductDAOImpl implements ProductDAO{
	
	@SuppressWarnings("unused")
	private DatabaseConnector dbc;
	private Connection c = null;
	private Logger logger = Logger.getLogger("dao.JDBCProductDAOImpl.class");
	private PreparedStatement createStatement;
	private PreparedStatement getPidAfterCreate;
	
	public JDBCProductDAOImpl(DatabaseConnector dbc) throws JDBCProductDAOImplException{
		logger.info("Initializing new JDBCInvoiceDAOImpl");
		this.dbc = dbc;

		try {
			c = dbc.getConnection();
		} catch (DatabaseConnectorException e) {
			logger.error("Error getting a Connection from Database");
			throw new JDBCProductDAOImplException("Could not get Database connection");
		}
		
		try {
			prepareStatements();
		} catch (SQLException e) {
			logger.error("Could not prepare statements");
			logger.debug(""+e.toString());
			throw new JDBCProductDAOImplException("Error preparing Statements");
		}
	}
	
	private void prepareStatements() throws SQLException{
		createStatement = c.prepareStatement("insert into products values (NULL,?,?,?,?,?)");
		getPidAfterCreate = c.prepareStatement("select pid from products where label = ? and" +
				" purchasePrice = ? and retailPrice = ? and supplier = ?");
		
	}

	@Override
	public Product createProduct(String label, double purchasePrice, double retailPrice,
	String supplier) throws JDBCProductDAOImplException {
		ProductImpl newProduct = createProductObject(label, purchasePrice, retailPrice, supplier);
		
		try {
			
			int invoiceid = writeProductToDatabase(newProduct);
			newProduct.setId(invoiceid);
		
		} catch (SQLException e) {
			logger.error("Could not insert new Product in to Database");
			throw new JDBCProductDAOImplException("Error inserting in to Database");
		}
		
		return newProduct;
	}

	private ProductImpl createProductObject(String label, double purchasePrice,
			double retailPrice, String supplier) {
		return new ProductImpl(label, purchasePrice, retailPrice, supplier, 0);
	}
	
	private int writeProductToDatabase(ProductImpl newProduct) throws SQLException{
		insertNewProduct(newProduct);
		int id = getIdOfJustInsertedProduct(newProduct);
		return id;
	}

	private void insertNewProduct(ProductImpl newProduct) throws SQLException {
		createStatement.setString(1,newProduct.getLabel());
		createStatement.setDouble(2,newProduct.getPurchasePrice());
		createStatement.setDouble(3,newProduct.getRetailPrice());
		createStatement.setString(4,newProduct.getSupplier());
		createStatement.setBoolean(5,true);
		
		createStatement.executeUpdate();
	}

	private int getIdOfJustInsertedProduct(ProductImpl newProduct) throws SQLException {
		getPidAfterCreate.setString(1,newProduct.getLabel());
		getPidAfterCreate.setDouble(2,newProduct.getPurchasePrice());
		getPidAfterCreate.setDouble(3,newProduct.getRetailPrice());
		getPidAfterCreate.setString(4,newProduct.getSupplier());
		
		ResultSet r = getPidAfterCreate.executeQuery();
		r.next();
		return r.getInt("pid");
	}
	
	
	@Override
	public void updateProduct(Product toUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product toDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Connection getConnection() {
		return c;
	}

}
