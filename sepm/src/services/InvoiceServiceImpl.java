package services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.InvoiceDAO;
import dao.InvoiceDAOException;

import entities.Consumption;
import entities.Invoice;

/**
 * The Class InvoiceServiceImpl.
 */
public class InvoiceServiceImpl implements InvoiceService{
	
	private InvoiceDAO dao;
	private Logger logger = Logger.getLogger("service.InvoiceServiceImpl.class");
	private ProductService ps;
	private Calendar cal = Calendar.getInstance();
	private String waiter = "default";
	
	/**
	 * Instantiates a new invoice service impl.
	 *
	 * @param dao the DataAccessObject
	 * @param ps the ProductService instance
	 */
	public InvoiceServiceImpl(InvoiceDAO dao, ProductService ps){
		this.dao = dao;
		this.ps = ps;
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#generateNewInvoice()
	 */
	@Override
	public Invoice generateNewInvoice() throws InvoiceServiceException {
		Invoice i = null;
		try {
			i = dao.createInvoice(getCurrentDate(), getCurrentTime(), waiter);
		} catch (InvoiceDAOException e) {
			logger.error("Could not persist Invoice");
			throw new InvoiceServiceException();
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#addProductToInvoice(int, entities.Invoice, int)
	 */
	@Override
	public void addProductToInvoice(int pid, Invoice i, int qty) {
		Consumption c = new Consumption(pid, qty, ps.getProductbyId(pid).getRetailPrice());
		i.getConsumptions().add(c);
		logger.debug("Just added pid = "+i.getConsumptions().get(0).getProductID());
		
		
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#closeInvoice(entities.Invoice)
	 */
	@Override
	public double closeInvoice(Invoice i) throws InvoiceServiceException {
		double sum = 0;
		try {
			logger.debug("Entering close Invoice");
			sum = calculateSum(i);
			i.setSum(sum);
			logger.debug("Sum set.");
			dao.updateInvoice(i);
		} catch (Exception e) {
			logger.error("Closing Invoice failed most likely DB but might be someone screwed with a closed invoice");
			throw new InvoiceServiceException();
		}
		return sum;
		
	}

	private double calculateSum(Invoice i) {
		logger.debug("Calculate sum got invoice id "+ i.getId());
		List<Consumption> cl = i.getConsumptions();
		Iterator<Consumption> iter = cl.listIterator();
		double sum = 0;
		while(iter.hasNext()){
			logger.debug("Entering Loop.");
			Consumption c = iter.next();
			sum += c.getPrice()*c.getQuantity();
		}
		logger.debug("Returning sum "+sum);
		return sum;
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#setWaiter(java.lang.String)
	 */
	@Override
	public void setWaiter(String name) {
		waiter = name;
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#getAllInvoices()
	 */
	@Override
	public List<Invoice> getAllInvoices() throws InvoiceServiceException {
		try {
			List<Invoice> result = dao.findAll();
			logger.debug("Fine till before Return");
			return result;
		} catch (InvoiceDAOException e) {
			logger.error("Could not perform a search.");
			throw new InvoiceServiceException();
		}
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#getInvoicesByDates(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill) throws InvoiceServiceException, IllegalArgumentException {
		List<Invoice> result = new LinkedList<Invoice>();
		Date startDate = Date.valueOf(datefrom);
		Date endDate = Date.valueOf(datetill);
		if(endDate.before(startDate)){
			throw new IllegalArgumentException();
		}
		
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		for(;!start.after(end);start.add(Calendar.DATE, 1)){
			Date  d = new Date(start.getTime().getTime());
			try {
				result.addAll(dao.findByDate(d.toString()));
			} catch (InvoiceDAOException e) {
				logger.error("Could not perform a search.");
				throw new InvoiceServiceException();
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see services.InvoiceService#getInvoiceById(int)
	 */
	@Override
	public Invoice getInvoiceById(int id) throws InvoiceServiceException {
		try {
			return dao.findById(id);
		} catch (InvoiceDAOException e) {
			logger.error("Could not perform a search.");
			throw new InvoiceServiceException();
		}
	}
	
	private String getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    return sdf.format(cal.getTime());
		
	}
	private String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(cal.getTime());
	}
}
