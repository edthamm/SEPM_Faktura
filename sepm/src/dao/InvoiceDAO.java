package dao;

import java.util.List;

import entities.Invoice;

public interface InvoiceDAO {
	public Invoice createInvoice(String date, String time, String waiter) throws JDBCInvoiceDAOImplException;
	public Invoice updateInvoice(Invoice toUpdate);
	public void deleteInvoice(Invoice toDelete);
	public List<Invoice> findAll();
	public Invoice findById(int id);
	public List<Invoice> findByDate(String date);
}
