package dao;

import java.util.List;

import entities.Product;

/**
 * The Interface ProductDAO.
 */
public interface ProductDAO {
	
	/**
	 * Creates the product and inserts its frame in to the Database.
	 *
	 * @param label the label
	 * @param purchasePrice the purchase price
	 * @param retailPrice the retail price
	 * @param supplier the supplier
	 * @return the product
	 * @throws ProductDAOException
	 */
	public Product createProduct(String label, double purchasePrice, double retailPrice,
			String supplier) throws ProductDAOException;
	
	/**
	 * Update product this will also handle closing the Product if a recently closed is updated which is
	 * still open in the DB.
	 *
	 * @param toUpdate the to update
	 * @throws ProductDAOException
	 */
	public void updateProduct(Product toUpdate) throws ProductDAOException;
	
	/**
	 * Delete product.
	 *
	 * @param toDelete the to delete
	 * @throws ProductDAOException
	 */
	public void deleteProduct(Product toDelete) throws ProductDAOException;
	
	/**
	 * Find all.
	 *
	 * @return the list of all products 
	 * @throws ProductDAOException if non found
	 */
	public List<Product> findAll() throws ProductDAOException;
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the product found 
	 * @throws ProductDAOException if non found
	 */
	public Product findById(int id) throws ProductDAOException;
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the list of products 
	 * @throws ProductDAOException if non found 
	 */
	public List<Product> findByName(String name) throws ProductDAOException;
}
