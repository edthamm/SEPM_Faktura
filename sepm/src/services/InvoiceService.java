package services;

import java.util.List;

import entities.Invoice;

public interface InvoiceService {

	public Invoice generateNewInvoice();
	public void addProductToInvoice(int pid, Invoice i, int qty);
	public double closeInvoice(Invoice i);
	public void setWaiter(String name);
	public List<Invoice> getAllInvoices();
	public List<Invoice> getInvoicesByWaiter(String waiter);
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill);
	public Invoice getInvoiceById(int id);

}
