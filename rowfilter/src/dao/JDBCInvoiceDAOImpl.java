/*
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import entities.Consumption;
import entities.Invoice;
import entities.InvoiceClosedException;
import entities.InvoiceImpl;


// TODO: Auto-generated Javadoc
/**
 * The Class JDBCInvoiceDAOImpl.
 */
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
	private PreparedStatement findConsumptionsOfInvoice;
	
	
	/**
	 * Instantiates a new jDBC invoice dao impl.
	 *
	 * @param dbc the DatabaseConnector 	 
	 * @throws JDBCInvoiceDAOImplException if no connection can be established or statements could not
	 * be prepared
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
	
	/**
	 * Prepare statements.
	 *
	 * @throws SQLException the sQL exception
	 */
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
		findConsumptionsOfInvoice = c.prepareStatement("select * from contains where iid = ?");
		
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
	
	/**
	 * Creates the invoice object.
	 *
	 * @param date the date
	 * @param time the time
	 * @param waiter the waiter
	 * @return the invoice impl
	 */
	private InvoiceImpl createInvoiceObject(String date, String time, String waiter) {
		return new InvoiceImpl(date,time,waiter,0);
	}

	/**
	 * Write invoice to database.
	 *
	 * @param newInvoice the new invoice
	 * @return the id of the new invoice
	 * @throws SQLException
	 */
	private int writeInvoiceToDatabase(Invoice newInvoice) throws SQLException {
		insertNewInvoice(newInvoice);
		int id = getIdOfJustInsertedInvoice(newInvoice);
		return id;
	}

	/**
	 * Insert new invoice.
	 *
	 * @param newInvoice the new invoice
	 * @throws SQLException 
	 */
	private void insertNewInvoice(Invoice newInvoice) throws SQLException {
		createStatement.setDate(1, newInvoice.getDate());
		createStatement.setString(2, newInvoice.getWaiter());
		createStatement.setTime(3, newInvoice.getTime());
		
		createStatement.executeUpdate();
	}
	
	/**
	 * Gets the id of just inserted invoice.
	 *
	 * @param newInvoice the new invoice
	 * @return the id of just inserted invoice
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

	/**
	 * Update open invoice.
	 *
	 * @param toUpdate the to update
	 * @throws SQLException
	 */
	private void updateOpenInvoice(Invoice toUpdate) throws SQLException {
		updateInvoice.setDate(1, toUpdate.getDate());
		updateInvoice.setString(2, toUpdate.getWaiter());
		updateInvoice.setTime(3, toUpdate.getTime());
		updateInvoice.setInt(4, 0);
		updateInvoice.setInt(5, toUpdate.getId());
		
		updateInvoice.executeUpdate();
		
	}

	/**
	 * Update and close invoice.
	 *
	 * @param toUpdate the to update
	 * @throws SQLException
	 */
	private void updateAndCloseInvoice(Invoice toUpdate) throws SQLException {
		
		updateInvoiceStoringSum(toUpdate);
		storeConsumptions(toUpdate);
	
	}

	/**
	 * Update invoice storing sum.
	 *
	 * @param toUpdate the Invoice to update
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
	 * Store consumptions.
	 *
	 * @param toUpdate the Invoice to update
	 * @throws SQLException
	 */
	private void storeConsumptions(Invoice toUpdate) throws SQLException {
		for(Consumption c : toUpdate.getConsumptions()){
			insertIntoContains.setInt(1, toUpdate.getId());
			insertIntoContains.setInt(2, c.getProductID());
			insertIntoContains.setInt(3, c.getQuantity());
			logger.debug("setting price to "+ c.getPrice());
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
	public List<Invoice> findAll() throws JDBCInvoiceDAOImplException {
		List<Invoice> result = new LinkedList<Invoice>();
		try {
			ResultSet r = findAll.executeQuery();
			constructInvoiceList(result, r);
		} catch (SQLException e) {
			logger.error("Got an SqlException on findAll");
			throw new JDBCInvoiceDAOImplException("Could not perform find all");
		} catch (InvoiceClosedException e) {
			logger.error("Got an Internal error on findAll");
			throw new JDBCInvoiceDAOImplException("Could not perform find all");
		}
		
		return result;
	}

	/**
	 * Construct invoice list.
	 *
	 * @param result the ResultList
	 * @param r the ResultSet
	 * @throws SQLException the sQL exception
	 * @throws InvoiceClosedException the invoice closed exception
	 */
	private void constructInvoiceList(List<Invoice> result, ResultSet r)
			throws SQLException, InvoiceClosedException {
		while(r.next()){
			Invoice i = extractInfoAndCreateInvoice(r);
			ifTheInvoiceIsClosedSetConsumptionsAndTotal(r,i);
			result.add(i);
		}
	}
	
	/**
	 * Extract info and create invoice.
	 *
	 * @param r the r
	 * @return the invoice
	 * @throws SQLException the sQL exception
	 */
	private Invoice extractInfoAndCreateInvoice(ResultSet r)
			throws SQLException {
		Invoice result = new InvoiceImpl(r.getDate("date").toString(),r.getTime("time").toString(),
										 r.getString("waiter"),r.getInt("iid"));
		return result;
	}
	
	/**
	 * If the invoice is closed set consumptions and total.
	 *
	 * @param r the r
	 * @param result the result
	 * @throws SQLException the sQL exception
	 * @throws InvoiceClosedException the invoice closed exception
	 */
	private void ifTheInvoiceIsClosedSetConsumptionsAndTotal(ResultSet r, Invoice result) throws SQLException,
	InvoiceClosedException {
		double total = r.getDouble("total");

		if(total != 0){
			addConsumptionsToInvoice(r.getInt("iid"), result);
		}

		result.setSum(total);
	}
	
	/**
	 * Adds the consumptions to invoice.
	 *
	 * @param id the id
	 * @param result the result
	 * @throws InvoiceClosedException the invoice closed exception
	 * @throws SQLException the sQL exception
	 */
	private void addConsumptionsToInvoice(int id, Invoice result)
			throws InvoiceClosedException, SQLException {
		logger.debug("Adding consumptions");
		List<Consumption> consumptions = getConsumptionsOfInvoice(id);
		result.setConsumptions(consumptions);
	}

	/**
	 * Gets the consumptions of invoice.
	 *
	 * @param id the id
	 * @return the consumptions of invoice
	 * @throws SQLException the sQL exception
	 */
	private List<Consumption> getConsumptionsOfInvoice(int id) throws SQLException {
		findConsumptionsOfInvoice.setInt(1, id);
		ResultSet r = findConsumptionsOfInvoice.executeQuery();
		List<Consumption> result = new LinkedList<Consumption>();
		while(r.next()){
			logger.debug("Adding consumption with: "+r.getInt("pid")+r.getInt("quantity")+r.getDouble("price"));
			Consumption c = new Consumption(r.getInt("pid"),r.getInt("quantity"),r.getDouble("price"));
			result.add(c);
		}
		
		return result;
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
	
	/**
	 * Find invoice by id.
	 *
	 * @param id the id
	 * @return the result set
	 * @throws SQLException the sQL exception
	 */
	private ResultSet findInvoiceByID(int id) throws SQLException {
		findByID.setInt(1, id);
		ResultSet r = findByID.executeQuery();
		return r;
	}
	
	@Override
	public List<Invoice> findByDate(String date) throws JDBCInvoiceDAOImplException {
		List<Invoice> result = new LinkedList<Invoice>();
		
		
		try {
			findByDate.setString(1,date);
			ResultSet r = findByDate.executeQuery();
			constructInvoiceList(result,r);
			
		} catch (SQLException e) {
			logger.error("Could Not execute findByDate got SqlException");
			logger.debug(""+e.toString());
			throw new JDBCInvoiceDAOImplException("Could not execute findByDate");
		} catch (InvoiceClosedException e) {
			logger.error("Could insert in to invoice something in the order of events got screwed");
			throw new JDBCInvoiceDAOImplException("Could not execute findByDate got an internal error");
		}
		
		return result;
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	protected Connection getConnection(){
		return c;
	}


}
