package services;

import java.util.List;

import org.apache.log4j.Logger;

import dao.JDBCProductDAOImpl;
import dao.JDBCProductDAOImplException;

import entities.Product;

public class ProductServiceImpl implements ProductService {

	private JDBCProductDAOImpl dao;
	private Logger logger = Logger.getLogger("services.ProductServiceImpl.class");

	public ProductServiceImpl(JDBCProductDAOImpl pdao) {
		this.dao = pdao;
	}

	@Override
	public List<Product> getAllProducts() {
		try {
			return dao.findAll();
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not retrive products from Database");
		}
		return null;
	}

	@Override
	public List<Product> getProductsbyLabel(String labelString) {
		try {
			return dao.findByName(labelString);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not retrive products from Database");
		}
		return null;
	}

	@Override
	public Product getProductbyId(int id) {
		try {
			return dao.findById(id);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not retrive products from Database");
		}
		return null;
	}

	@Override
	public void increasePriceByFivePercent(Product p) throws ProductServiceException {
		//TODO ask about rounding
		p.setRetailPrice(p.getRetailPrice()*1.05);
		try {
			dao.updateProduct(p);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not persist Product");
			throw new ProductServiceException("Could not persist product");
		}
	}

	@Override
	public void increasePriceByFivePercent(List<Product> l) throws ProductServiceException {
		try{
			for(Product p: l){
				increasePriceByFivePercent(p);
			}
		}
		catch(Exception e){
			throw new ProductServiceException("Could not persist product");

		}
	}

	@Override
	public Product generateNewProduct() throws ProductServiceException {
		try {
			Product p = dao.createProduct("Bezeichnung", 1, 1, "Lieferant");
			return p;
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could notcreate Product");
			throw new ProductServiceException("Could not create product");
		}
		
	}

	@Override
	public void updateProduct(Product p) throws ProductServiceException {
		try {
			dao.updateProduct(p);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not persist Product");
			throw new ProductServiceException("Could not persist product");
		}
		
	}

	@Override
	public void deleteProduct(Product p) throws ProductServiceException {
		try {
			dao.deleteProduct(p);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not delete Product");
			throw new ProductServiceException("Could not delete product");
		}
		
	}
	

}
