package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import entities.Invoice;
import entities.InvoiceImpl;


public class JDBCInvoiceDAOImpl implements InvoiceDAO {
	
	@SuppressWarnings("unused")
	private DatabaseConnector dbc;
	private Connection c = null;
	private Logger logger = Logger.getLogger("dao.JDBCInvoiceDAOImpl.class");
	
	private PreparedStatement createStatement;
	private PreparedStatement getIdAfterCreate;
	
	/**
	 * 
	 * @param dbc
	 * @throws JDBCInvoiceDAOImplException
	 */
	public JDBCInvoiceDAOImpl(DatabaseConnector dbc) throws JDBCInvoiceDAOImplException{
		logger.info("Initializing new JDBCInvoiceDAOImpl");
		this.dbc = dbc;

		try {
			c = dbc.getConnection();
		} catch (DatabaseConnectorException e) {
			logger.error("Error getting a Connection from Database");
			throw new JDBCInvoiceDAOImplException("Could not get Database connection");
		}
		
		try {
			prepareStatements();
		} catch (SQLException e) {
			logger.error("Could not prepare statements");
			throw new JDBCInvoiceDAOImplException("Error preparing Statements");
		}
	}
	
	private void prepareStatements() throws SQLException {
		createStatement = c.prepareStatement("INSERT INTO INVOICE VALUES (NULL,?,0,?,?)");//date waiter time
		getIdAfterCreate = c.prepareStatement("SELECT iid FROM invoice WHERE total = 0 AND" +
											  " date = ? AND waiter = ? AND time = ?");
		
	}

	@Override
	public Invoice createInvoice(String date, String time, String waiter) throws JDBCInvoiceDAOImplException {
		
		InvoiceImpl newInvoice = createInvoiceObject(date, time, waiter);
		
		try {
			
			int invoiceid = writeInvoiceToDatabase(newInvoice);
			newInvoice.setId(invoiceid);
		
		} catch (SQLException e) {
			logger.error("Could not insert new Invoice in to Database");
			throw new JDBCInvoiceDAOImplException("Error inserting in to Database");
		}
		
		return newInvoice;
	}

	private InvoiceImpl createInvoiceObject(String date, String time, String waiter) {
		return new InvoiceImpl(date,time,waiter,0);
	}

	private int writeInvoiceToDatabase(Invoice newInvoice) throws SQLException {
		createStatement.setDate(1, newInvoice.getDate());
		createStatement.setString(2, newInvoice.getWaiter());
		createStatement.setTime(3, newInvoice.getTime());
		
		createStatement.executeUpdate();
		
		getIdAfterCreate.setDate(1, newInvoice.getDate());
		getIdAfterCreate.setString(2, newInvoice.getWaiter());
		getIdAfterCreate.setTime(3, newInvoice.getTime());
		
		ResultSet r = getIdAfterCreate.executeQuery();
		r.next();
		int id = r.getInt("iid");
		logger.debug("Returning id: "+id);
		return id;
	}

	@Override
	public Invoice updateInvoice(Invoice toUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInvoice(Invoice toDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Invoice> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invoice> findByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Connection getConnection(){
		return c;
	}


}
