package services;

import entities.Invoice;

public interface InvoiceService {

	public Invoice generateNewInvoice();
	public void addProductToInvoice(int pid, int iid, int qty);
	public void closeInvoice(Integer selectedItem);
	public void setWaiter(String name);

}
