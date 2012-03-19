package services;

import java.util.List;

import org.apache.log4j.Logger;

import dao.InvoiceDAO;
import dao.InvoiceDAOException;

import entities.Invoice;

public class InvoiceServiceImpl implements InvoiceService{
	
	private InvoiceDAO dao;
	private Logger logger = Logger.getLogger("service.InvoiceServiceImpl.class");
	
	public InvoiceServiceImpl(InvoiceDAO dao){
		this.dao = dao;
	}

	@Override
	public Invoice generateNewInvoice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProductToInvoice(int pid, int iid, int qty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInvoice(Integer selectedItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWaiter(String name) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice getInvoiceById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
