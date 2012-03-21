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
	public void increasePriceByFivePercent(Product p) {
		//TODO ask about rounding
		p.setRetailPrice(p.getRetailPrice()*1.05);
		try {
			dao.updateProduct(p);
		} catch (JDBCProductDAOImplException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void increasePriceByFivePercent(List<Product> l) {
		for(Product p: l){
			increasePriceByFivePercent(p);
		}
	}

	@Override
	public Product generateNewProduct() {
		try {
			Product p = dao.createProduct("Bezeichnung", 1, 1, "Lieverant");
			return p;
		} catch (JDBCProductDAOImplException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	

}
