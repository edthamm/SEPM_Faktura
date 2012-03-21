package services;

import java.util.List;

import entities.Invoice;

public interface InvoiceService {

	public Invoice generateNewInvoice() throws InvoiceServiceException;
	public void addProductToInvoice(int pid, Invoice i, int qty);
	public double closeInvoice(Invoice i) throws InvoiceServiceException;
	public void setWaiter(String name);
	public List<Invoice> getAllInvoices() throws InvoiceServiceException;
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill) throws InvoiceServiceException, IllegalArgumentException;
	public Invoice getInvoiceById(int id) throws InvoiceServiceException;

}
