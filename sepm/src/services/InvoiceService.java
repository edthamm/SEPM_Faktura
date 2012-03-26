package services;

import java.util.List;

import entities.Invoice;

/**
 * The Interface InvoiceService.
 */
public interface InvoiceService {

	/**
	 * Generate new invoice.
	 *
	 * @return the new invoice
	 * @throws InvoiceServiceException if the invoice can not be stored
	 */
	public Invoice generateNewInvoice() throws InvoiceServiceException;
	
	/**
	 * Adds the product to invoice.
	 *
	 * @param pid the products ID
	 * @param i the Invoice
	 * @param qty the quantity to add
	 */
	public void addProductToInvoice(int pid, Invoice i, int qty);
	
	/**
	 * Close invoice.
	 *
	 * @param i the Invoice
	 * @return the subtotal of the Invoice
	 * @throws InvoiceServiceException if the invoice can not be stored
	 */
	public double closeInvoice(Invoice i) throws InvoiceServiceException;
	
	/**
	 * Sets the waiter.
	 *
	 * @param name of the new waiter
	 */
	public void setWaiter(String name);
	
	/**
	 * Gets the all invoices.
	 *
	 * @return a list of all invoices
	 * @throws InvoiceServiceException if there was an error while performing the search
	 */
	public List<Invoice> getAllInvoices() throws InvoiceServiceException;
	
	/**
	 * Gets the invoices by dates.
	 *
	 * @param datefrom the date from which on invoices should be included
	 * @param datetill the date up to which invoices should be included
	 * @return the list of invoices by dates
	 * @throws InvoiceServiceException if there was an error while performing the search
	 * @throws IllegalArgumentException if the date string is not parseable or the end date is prior to the start date
	 */
	public List<Invoice> getInvoicesByDates(String datefrom, String datetill) throws InvoiceServiceException, IllegalArgumentException;
	
	/**
	 * Gets the invoice by id.
	 *
	 * @param id the id of the invoice
	 * @return the invoice with the given id
	 * @throws InvoiceServiceException if there is no invoice with the given id or the search could not be performed
	 */
	public Invoice getInvoiceById(int id) throws InvoiceServiceException;

	/**
	 * Gets the list of open invoices.
	 *
	 * @return the list of open invoices
	 */
	public List<Invoice> getListOfOpenInvoices();

	/**
	 * Sets the open invoices.
	 *
	 * @param openInvoices the new open invoices
	 */
	public void setOpenInvoices(List<Invoice> openInvoices);

}
