package dao;

import java.util.List;

import entities.Product;

public interface ProductDAO {
	public Product createProduct(String label, double purchasePrice, double retailPrice,
			String supplier) throws ProductDAOException;
	public void updateProduct(Product toUpdate) throws ProductDAOException;
	public void deleteProduct(Product toDelete) throws ProductDAOException;
	public List<Product> findAll() throws ProductDAOException;
	public Product findById(int id) throws ProductDAOException;
	public List<Product> findByName(String name) throws ProductDAOException;
}
