package services;

import java.util.List;

import entities.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	public List<Product> getProductsbyLabel(String labelString);

	public Product getProductbyId(int id);

	public void setInvoiceService(InvoiceService is);

}
