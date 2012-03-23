package services;

import java.util.List;

import entities.Product;

/**
 * The Interface ProductService.
 */
public interface ProductService {

	/**
	 * Gets the all products.
	 *
	 * @return a list of all products
	 */
	public List<Product> getAllProducts();

	/**
	 * Gets the products by label.
	 *
	 * @param label String the label/name of the product
	 * @return the list of products products with this label or null if non exist
	 */
	public List<Product> getProductsbyLabel(String labelString);

	/**
	 * Gets the product by id.
	 *
	 * @param id the id of the product
	 * @return the list of products products with this id or null if non exist
	 */
	public Product getProductbyId(int id);

	/**
	 * Increase price by five percent.
	 *
	 * @param p the product
	 * @throws ProductServiceException if the update could not be performed
	 */
	public void increasePriceByFivePercent(Product p) throws ProductServiceException;

	/**
	 * Increase price by five percent.
	 * This is non atomic in the sense that a failure to update will abort the process. 
	 * Some of the products in the list may have been updated but some may not.
	 *
	 * @param l the list of products to be updated
	 * @throws ProductServiceException if one update fails and aborts on failure.
	 */
	public void increasePriceByFivePercent(List<Product> l) throws ProductServiceException;

	/**
	 * Generate new product.
	 *
	 * @return the new product
	 * @throws ProductServiceException the product could not be stored
	 */
	public Product generateNewProduct() throws ProductServiceException;

	/**
	 * Update product.
	 *
	 * @param p the product
	 * @throws ProductServiceException if the update could not be performed
	 */
	public void updateProduct(Product p) throws ProductServiceException;

	/**
	 * Delete product.
	 *
	 * @param p the produtc
	 * @throws ProductServiceException if the deletion could not be performed
	 */
	public void deleteProduct(Product p) throws ProductServiceException;
}
