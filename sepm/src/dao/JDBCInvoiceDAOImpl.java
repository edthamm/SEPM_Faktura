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
	private PreparedStatement updateInvoice;
	private PreparedStatement insertIntoContains;
	
	
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
			logger.debug(""+e.toString());
			throw new JDBCInvoiceDAOImplException("Error preparing Statements");
		}
	}
	
	private void prepareStatements() throws SQLException {
		createStatement = c.prepareStatement("INSERT INTO INVOICE VALUES (NULL,?,0,?,?)");//date waiter time
		getIdAfterCreate = c.prepareStatement("SELECT iid FROM invoice WHERE total = 0 AND" +
											  " date = ? AND waiter = ? AND time = ?");
		updateInvoice = c.prepareStatement("UPDATE INVOICE SET date = ?, waiter = ?, time = ?, total = ? where iid = ?");
		insertIntoContains = c.prepareStatement("INSERT INTO contains VALUES (?,?,?,?)");
		
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
		insertNewInvoice(newInvoice);
		int id = getIdOfJustInsertedInvoice(newInvoice);
		return id;
	}

	/**
	 * @param newInvoice
	 * @throws SQLException
	 */
	private void insertNewInvoice(Invoice newInvoice) throws SQLException {
		createStatement.setDate(1, newInvoice.getDate());
		createStatement.setString(2, newInvoice.getWaiter());
		createStatement.setTime(3, newInvoice.getTime());
		
		createStatement.executeUpdate();
	}
	
	/**
	 * @param newInvoice
	 * @return
	 * @throws SQLException
	 */
	private int getIdOfJustInsertedInvoice(Invoice newInvoice)
			throws SQLException {
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
	public Invoice updateInvoice(Invoice toUpdate) throws JDBCInvoiceDAOImplException {
		try{
		if(toUpdate.getSum() == 0){
			updateOpenInvoice(toUpdate);
		}
		else{
			updateAndCloseInvoice();
		}
		}
		catch(SQLException e){
			logger.error("Error updateing Invoice");
			throw new JDBCInvoiceDAOImplException("Could not update Invoice");
		}
		return toUpdate;
	}

	private void updateOpenInvoice(Invoice toUpdate) throws SQLException {
		updateInvoice.setDate(1, toUpdate.getDate());
		updateInvoice.setString(2, toUpdate.getWaiter());
		updateInvoice.setTime(3, toUpdate.getTime());
		updateInvoice.setInt(4, 0);
		updateInvoice.setInt(5, toUpdate.getId());
		
	}

	private void updateAndCloseInvoice() {
		// TODO Auto-generated method stub
		
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
