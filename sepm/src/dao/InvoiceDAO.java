package dao;

import java.sql.Date;
import java.util.List;

import entities.Invoice;

public interface InvoiceDAO {
	public int createInvoice();
	public Invoice updateInvoice(Invoice toUpdate);
	public void deleteInvoice(Invoice toDelete);
	public List<Invoice> findAll();
	public Invoice findById(int id);
	public List<Invoice> findByDate(Date date);
}
