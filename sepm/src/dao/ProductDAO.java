package dao;

import java.util.List;

import entities.Product;

public interface ProductDAO {
	public int createProduct();
	public void updateProduct(Product toUpdate);
	public void deleteProduct(Product toDelete);
	public List<Product> findAll();
	public Product findById(int id);
	public List<Product> findByName(String name);
}
