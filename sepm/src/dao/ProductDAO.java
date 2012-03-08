package dao;

import java.util.List;

import entities.ProductImpl;

public interface ProductDAO {
	public int createProduct();
	public void updateProduct(ProductImpl toUpdate);
	public void deleteProduct(ProductImpl toDelete);
	public List<ProductImpl> findAll();
	public ProductImpl findById(int id);
	public List<ProductImpl> findByName(String name);
}
