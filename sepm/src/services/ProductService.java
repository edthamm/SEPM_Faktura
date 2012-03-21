package services;

import java.util.List;

import entities.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	public List<Product> getProductsbyLabel(String labelString);

	public Product getProductbyId(int id);

	public void increasePriceByFivePercent(Product p) throws ProductServiceException;

	public void increasePriceByFivePercent(List<Product> l) throws ProductServiceException;

	public Product generateNewProduct() throws ProductServiceException;

	public void updateProduct(Product p) throws ProductServiceException;

	public void deleteProduct(Product p) throws ProductServiceException;
}
