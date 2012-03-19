package services;

import java.util.List;

import entities.Invoice;

public interface InvoiceService {

	public Invoice generateNewInvoice();
	public void addProductToInvoice(int pid, int iid, int qty);
	public double closeInvoice(Integer selectedItem);
	public void setWaiter(String name);
	public List<Invoice> getAllInvoices();
	public List<Invoice> getInvoicesByWaiter(String waiter);
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill);
	public Invoice getInvoiceById(int id);

}
