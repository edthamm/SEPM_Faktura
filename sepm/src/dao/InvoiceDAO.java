package dao;

import java.util.List;

import entities.Invoice;

public interface InvoiceDAO {
	public Invoice createInvoice(String date, String time, String waiter) throws InvoiceDAOException;
	public Invoice updateInvoice(Invoice toUpdate) throws InvoiceDAOException;
	public void deleteInvoice(Invoice toDelete) throws InvoiceDAOException;
	public List<Invoice> findAll() throws InvoiceDAOException;;
	public Invoice findById(int id) throws InvoiceDAOException;;
	public List<Invoice> findByDate(String date) throws InvoiceDAOException;;
}
