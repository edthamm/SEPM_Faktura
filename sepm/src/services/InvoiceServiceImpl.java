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

public class InvoiceServiceImpl implements InvoiceService{
	
	private InvoiceDAO dao;
	private Logger logger = Logger.getLogger("service.InvoiceServiceImpl.class");
	private ProductService ps;
	private Calendar cal = Calendar.getInstance();
	private String waiter = "default";
	
	public InvoiceServiceImpl(InvoiceDAO dao, ProductService ps){
		this.dao = dao;
		this.ps = ps;
	}

	@Override
	public Invoice generateNewInvoice() {
		try {
			return dao.createInvoice(getCurrentDate(), getCurrentTime(), waiter);
		} catch (InvoiceDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addProductToInvoice(int pid, int iid, int qty) {
		Invoice i;
		try {
			i = dao.findById(iid);
			Consumption c = new Consumption(pid, qty, ps.getProductbyId(pid).getRetailPrice());
			i.getConsumptions().add(c);
		} catch (InvoiceDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public double closeInvoice(Integer id) {
		try {
			Invoice i = dao.findById(id);
			double sum = calculateSum(i);
			i.setSum(sum);
			dao.updateInvoice(i);
			return sum;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
		
	}

	private double calculateSum(Invoice i) {
		List<Consumption> cl = i.getConsumptions();
		Iterator<Consumption> iter = cl.listIterator();
		double sum = 0;
		while(iter.hasNext()){
			Consumption c = iter.next();
			sum += c.getPrice();
		}
		return sum;
	}

	@Override
	public void setWaiter(String name) {
		waiter = name;
	}

	@Override
	public List<Invoice> getAllInvoices() {
		try {
			List<Invoice> result = dao.findAll();
			logger.debug("Fine till before Return");
			return result;
		} catch (InvoiceDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.error("should not get here");
		return null;
	}

	@Override
	public List<Invoice> getInvoicesByWaiter(String waiter) {
		//TODO
		return null;
	}

	@Override
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill) {
		List<Invoice> result = new LinkedList<Invoice>();
		Date startDate = Date.valueOf(datefrom);
		Date endDate = Date.valueOf(datetill);
		if(endDate.before(startDate)){
			//TODO
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Invoice getInvoiceById(int id) {
		try {
			return dao.findById(id);
		} catch (InvoiceDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
