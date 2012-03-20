package dao;

import java.util.List;

import entities.Invoice;

/**
 * The Interface InvoiceDAO.
 */
public interface InvoiceDAO {
	
	/**
	 * Creates the invoice.
	 *
	 * @param date the date
	 * @param time the time
	 * @param waiter the waiter
	 * @return the invoice
	 * @throws InvoiceDAOException - if the Invoice can not be made persistent 
	 */
	public Invoice createInvoice(String date, String time, String waiter) throws InvoiceDAOException;
	
	/**
	 * Update invoice.
	 *
	 * @param toUpdate the to update
	 * @return the invoice that was updated (non-atomic)
	 * @throws InvoiceDAOException 
	 */
	public Invoice updateInvoice(Invoice toUpdate) throws InvoiceDAOException;
	
	/**
	 * Delete invoice.
	 *
	 * @param toDelete the to delete
	 * @throws InvoiceDAOException
	 */
	public void deleteInvoice(Invoice toDelete) throws InvoiceDAOException;
	
	/**
	 * Find all.
	 *
	 * @return the list 
	 * @throws InvoiceDAOException if non found
	 */
	public List<Invoice> findAll() throws InvoiceDAOException;;
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the invoice
	 * @throws InvoiceDAOException if non found
	 */
	public Invoice findById(int id) throws InvoiceDAOException;;
	
	/**
	 * Find by date.
	 *
	 * @param date the date
	 * @return the list 
	 * @throws InvoiceDAOException if non found 
	 */
	public List<Invoice> findByDate(String date) throws InvoiceDAOException;;
}
