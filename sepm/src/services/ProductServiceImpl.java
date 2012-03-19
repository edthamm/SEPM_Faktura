package services;

import java.util.List;

import dao.JDBCProductDAOImpl;
import dao.JDBCProductDAOImplException;

import entities.Product;

public class ProductServiceImpl implements ProductService {

	private JDBCProductDAOImpl dao;

	public ProductServiceImpl(JDBCProductDAOImpl pdao) {
		this.dao = pdao;
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
		try {
			return dao.findById(id);
		} catch (JDBCProductDAOImplException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setInvoiceService(InvoiceService is) {
		// TODO Auto-generated method stub
		
	}

}
