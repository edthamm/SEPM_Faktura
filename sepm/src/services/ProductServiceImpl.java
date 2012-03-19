package services;

import java.util.List;

import dao.JDBCProductDAOImpl;
import dao.JDBCProductDAOImplException;

import entities.Product;

public class ProductServiceImpl implements ProductService {

	private JDBCProductDAOImpl dao;
	private InvoiceService is;

	public ProductServiceImpl(JDBCProductDAOImpl pdao) {
		this.dao = pdao;
	}

	@Override
	public List<Product> getAllProducts() {
		try {
			return dao.findAll();
		} catch (JDBCProductDAOImplException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getProductsbyLabel(String labelString) {
		try {
			return dao.findByName(labelString);
		} catch (JDBCProductDAOImplException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		this.is = is;
		
	}

}
