package entities;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class InvoiceImpl implements Invoice{
	private int id = 0;
	private double sum = 0;
	private Date date = Date.valueOf("2000-01-01");
	private Time time = Time.valueOf("00:00:00");
	private String waiter = "";
	private HashMap<String,Integer> consumptions = new HashMap<String,Integer>();
	private Logger logger = Logger.getLogger("entities.InvoiceImpl.class");
	private boolean open = true;
	
	public InvoiceImpl(){
		logger.debug("Initialize InvoiceImpl to default");
	}
	
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
	
	public double getSum() {
		return sum;
	}
	/**
	 * 
	 * @param sum
	 * @throws InvoiceClosedException
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
	 * @throws InvoiceClosedException 
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
	 * @throws InvoiceClosedException
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
	 * @throws InvoiceClosedException
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

	public HashMap<String, Integer> getConsumptions() {
		return consumptions;
	}
	/**
	 * 
	 * @param consumptions
	 * @throws InvoiceClosedException
	 */
	public void setConsumptions(HashMap<String, Integer> consumptions) throws InvoiceClosedException {
		if(open){
			this.consumptions = consumptions;
		}
		else{
			logger.warn("Attempted reseting of consumptions.");
			throw new InvoiceClosedException();
		}
	}
}
