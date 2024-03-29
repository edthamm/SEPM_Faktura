package entities;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class InvoiceImpl implements Invoice{
	private int id = 0;
	private double sum = 0;
	private Date date = Date.valueOf("2000-01-01");
	private Time time = Time.valueOf("00:00:00");
	private String waiter = "";
	private List<Consumption> consumptions = new LinkedList<Consumption>();
	private Logger logger = Logger.getLogger("entities.InvoiceImpl.class");
	private boolean open = true;
	
	public InvoiceImpl(){
		logger.debug("Initialize InvoiceImpl to default");
	}
	
	/**
	 * Instantiates a new invoice impl.
	 *
	 * @param date the date of creation
	 * @param time the time of creation
	 * @param waiter the waiter
	 * @param id the id
	 */
	public InvoiceImpl(String date, String time, String waiter, int id){
		logger.debug("Initializing InvoiceImpl with data");
		this.date = Date.valueOf(date);
		this.time = Time.valueOf(time);
		this.waiter = waiter;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public double getSum() {
		return sum;
	}
	/**
	 * 
	 * @param sum
	 * @throws InvoiceClosedException if Invoice is closed
	 */
	public void setSum(double sum) throws InvoiceClosedException {
		if(open){
			logger.debug("Closing InvoiceImpl.");
			this.sum = sum;
			open = false;
		}
		else{
			logger.warn("Attempted reseting of sum.");
			throw new InvoiceClosedException();
		}
	}
	
	public Date getDate() {
		return date;
	}
	/**
	 * 
	 * 
	 * @param date
	 * @throws InvoiceClosedException if Invoice is closed
	 */
	public void setDate(Date date) throws InvoiceClosedException {
		if (open) {
			this.date = date;
		}
		else{
			logger.warn("Attempted reseting of Date.");
			throw new InvoiceClosedException();
		}
	}
	
	public Time getTime() {
		return time;
	}
	/**
	 * 
	 * @param time
	 * @throws InvoiceClosedException if Invoice is closed
	 */
	public void setTime(Time time) throws InvoiceClosedException {
		if (open) {
			this.time = time;
		}
		else{
			logger.warn("Attempted reseting of Time");
			throw new InvoiceClosedException();
		}
	}
	
	public String getWaiter() {
		return waiter;
	}
	/**
	 * 
	 * @param waiter
	 * @throws InvoiceClosedException if Invoice is closed
	 */
	public void setWaiter(String waiter) throws InvoiceClosedException {
		if (open) {
			this.waiter = waiter;
		}
		else{
			logger.warn("Attempted reseting of waiter.");
			throw new InvoiceClosedException();
		}
	}

	public List<Consumption> getConsumptions() {
		return consumptions;
	}
	/**
	 * 
	 * @param consumptions
	 * @throws InvoiceClosedException if Invoice is closed
	 */
	public void setConsumptions(List<Consumption> consumptions) throws InvoiceClosedException {
		if(open){
			this.consumptions = consumptions;
		}
		else{
			logger.warn("Attempted reseting of consumptions.");
			throw new InvoiceClosedException();
		}
	}
}
