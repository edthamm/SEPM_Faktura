package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import entities.Consumption;
import entities.Invoice;
import entities.InvoiceClosedException;
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
	private PreparedStatement findAll;
	private PreparedStatement findByID;
	private PreparedStatement findByDate;
	private PreparedStatement delete;
	
	
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
		delete = c.prepareStatement("delete from invoice where iid = ? and total = 0");
		findAll = c.prepareStatement("select * from invoice");
		findByID = c.prepareStatement("select * from invoice where iid = ?");
		findByDate = c.prepareStatement("select * from invoice where date = ?");
		
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
			updateAndCloseInvoice(toUpdate);
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
		
		updateInvoice.executeUpdate();
		
	}

	private void updateAndCloseInvoice(Invoice toUpdate) throws SQLException {
		
		updateInvoiceStoringSum(toUpdate);
		storeConsumptions(toUpdate);
	
	}

	/**
	 * @param toUpdate
	 * @throws SQLException
	 */
	private void updateInvoiceStoringSum(Invoice toUpdate)
			throws SQLException {
		updateInvoice.setDate(1, toUpdate.getDate());
		updateInvoice.setString(2, toUpdate.getWaiter());
		updateInvoice.setTime(3, toUpdate.getTime());
		updateInvoice.setDouble(4, toUpdate.getSum());
		updateInvoice.setInt(5, toUpdate.getId());
		
		updateInvoice.executeUpdate();
	}

	/**
	 * @param toUpdate
	 * @throws SQLException
	 */
	private void storeConsumptions(Invoice toUpdate) throws SQLException {
		for(Consumption c : toUpdate.getConsumptions()){
			insertIntoContains.setInt(1, toUpdate.getId());
			insertIntoContains.setInt(2, c.getProductID());
			insertIntoContains.setInt(3, c.getQuantity());
			insertIntoContains.setDouble(4, c.getPrice());
			
			insertIntoContains.executeUpdate();
		}
	}
	
	
	@Override
	public void deleteInvoice(Invoice toDelete) throws JDBCInvoiceDAOImplException {
		try {
			delete.setInt(1, toDelete.getId());
			delete.executeUpdate();
		} catch (SQLException e) {
			logger.error("SqlException on delete");
			logger.debug(""+e.toString());
			throw new JDBCInvoiceDAOImplException("Could not delete Invoice");
		}
		
		
	}

	@Override
	public List<Invoice> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Invoice extractInfoAndCreateInvoice(ResultSet r)
			throws SQLException {
		Invoice result = new InvoiceImpl(r.getDate("date").toString(),r.getTime("time").toString(),
										 r.getString("waiter"),r.getInt("iid"));
		return result;
	}
	
	private void ifTheInvoiceIsClosedSetConsumptionsAndTotal(ResultSet r, Invoice result) throws SQLException,
	InvoiceClosedException {
		double total = r.getDouble("total");

		if(total != 0){
			addConsumptionsToInvoice(r.getInt("iid"), result);
		}

		result.setSum(total);
	}
	private void addConsumptionsToInvoice(int id, Invoice result)
			throws InvoiceClosedException {
		List<Consumption> consumptions = getConsumptionsOfInvoice(id);
		result.setConsumptions(consumptions);
	}

	private List<Consumption> getConsumptionsOfInvoice(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Invoice findById(int id) throws JDBCInvoiceDAOImplException {
	try {
		
			ResultSet r = findInvoiceByID(id);
			r.next();
			Invoice result = extractInfoAndCreateInvoice(r);
			ifTheInvoiceIsClosedSetConsumptionsAndTotal(r, result);
			return result;
			
		} catch (SQLException e) {
			logger.error("Could Not execute findById got SqlException");
			throw new JDBCInvoiceDAOImplException("Could not execute findById");
		} catch (InvoiceClosedException e) {
			logger.error("Could insert in to invoice something in the order of events got screwed");
			throw new JDBCInvoiceDAOImplException("Could not execute findById got an internal error");
		}
	}
	
	private ResultSet findInvoiceByID(int id) throws SQLException {
		findByID.setInt(1, id);
		ResultSet r = findByID.executeQuery();
		return r;
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
