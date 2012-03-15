package entities;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface Invoice {
	public int getId() ;
	public double getSum() ;
	public void setSum(double sum) throws InvoiceClosedException ;
	public Date getDate() ;
	public void setDate(Date date) throws InvoiceClosedException ;
	public Time getTime() ;
	public void setTime(Time time) throws InvoiceClosedException ;
	public String getWaiter() ;
	public void setWaiter(String waiter) throws InvoiceClosedException;
	public List<Consumption> getConsumptions() ;
	public void setConsumptions(List<Consumption> consumptions) throws InvoiceClosedException ;
}