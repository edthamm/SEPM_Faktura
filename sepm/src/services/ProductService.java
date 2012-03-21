package services;

import java.util.List;

import entities.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	public List<Product> getProductsbyLabel(String labelString);

	public Product getProductbyId(int id);

	public void increasePriceByFivePercent(Product p);

	public void increasePriceByFivePercent(List<Product> l);

	public Product generateNewProduct();
}
