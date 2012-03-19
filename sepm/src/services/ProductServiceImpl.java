package services;

import java.util.List;

import dao.JDBCProductDAOImpl;

import entities.Product;

public class ProductServiceImpl implements ProductService {

	public ProductServiceImpl(JDBCProductDAOImpl pdao) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsbyLabel(String labelString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductbyId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInvoiceService(InvoiceService is) {
		// TODO Auto-generated method stub
		
	}

}
