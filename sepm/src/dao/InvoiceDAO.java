package dao;

import java.sql.Date;
import java.util.List;

import entities.InvoiceImpl;

public interface InvoiceDAO {
	public int createInvoice();
	public InvoiceImpl updateInvoice(InvoiceImpl toUpdate);
	public void deleteInvoice(InvoiceImpl toDelete);
	public List<InvoiceImpl> findAll();
	public InvoiceImpl findById(int id);
	public List<InvoiceImpl> findByDate(Date date);
}
