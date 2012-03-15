package dao;

import java.util.List;

import entities.Invoice;

public interface InvoiceDAO {
	public Invoice createInvoice(String date, String time, String waiter) throws InvoiceDAOException;
	public Invoice updateInvoice(Invoice toUpdate) throws InvoiceDAOException;
	public void deleteInvoice(Invoice toDelete);
	public List<Invoice> findAll();
	public Invoice findById(int id);
	public List<Invoice> findByDate(String date);
}
