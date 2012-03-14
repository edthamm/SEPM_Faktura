package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import entities.Invoice;
import entities.InvoiceImpl;


public class JDBCInvoiceDAOImpl implements InvoiceDAO {
	
	private DatabaseConnector dbc;
	private Connection c = null;
	private Logger logger = Logger.getLogger("dao.JDBCInvoiceDAOImpl.class");
	
	public JDBCInvoiceDAOImpl(DatabaseConnector dbc) throws JDBCInvoiceDAOImplException{
		logger.info("Initializing new JDBCInvoiceDAOImpl");
		this.dbc = dbc;

		try {
			c = dbc.getConnection();
		} catch (DatabaseConnectorException e) {
			logger.error("Error getting a Connection from Database");
			throw new JDBCInvoiceDAOImplException("Could not get Database connection");
		}
	}

	@Override
	public Invoice createInvoice(String date, String time, String waiter) {
		// TODO Auto-generated method stub
		InvoiceImpl newInvoice = createInvoiceObject(date, time, waiter);
		int invoiceid = writeInvoiceToDatabase(newInvoice);
		newInvoice.setId(invoiceid);
		return newInvoice;
	}

	private InvoiceImpl createInvoiceObject(String date, String time, String waiter) {
		return new InvoiceImpl(date,time,waiter,0);
	}

	private int writeInvoiceToDatabase(Invoice newInvoice) {
		// TODO Auto-generated method stub
		return 0;
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
	public List<Invoice> findByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Connection getConnection(){
		return c;
	}


}
