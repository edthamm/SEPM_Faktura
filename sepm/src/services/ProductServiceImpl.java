package services;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;

import dao.JDBCProductDAOImpl;
import dao.JDBCProductDAOImplException;

import entities.Product;

/**
 * The Class ProductServiceImpl.
 */
public class ProductServiceImpl implements ProductService {

	private JDBCProductDAOImpl dao;
	private Logger logger = Logger.getLogger("services.ProductServiceImpl.class");

	/**
	 * Instantiates a new product service.
	 *
	 * @param pdao the DataAccessObject
	 */
	public ProductServiceImpl(JDBCProductDAOImpl pdao) {
		this.dao = pdao;
	}

	/* (non-Javadoc)
	 * @see services.ProductService#getAllProducts()
	 */
	@Override
	public List<Product> getAllProducts() {
		try {
			return dao.findAll();
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not retrive products from Database");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see services.ProductService#getProductsbyLabel(java.lang.String)
	 */
	@Override
	public List<Product> getProductsbyLabel(String labelString) {
		try {
			return dao.findByName(labelString);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not retrive products from Database");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see services.ProductService#getProductbyId(int)
	 */
	@Override
	public Product getProductbyId(int id) {
		try {
			return dao.findById(id);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not retrive products from Database");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see services.ProductService#increasePriceByFivePercent(entities.Product)
	 */
	@Override
	public void increasePriceByFivePercent(Product p) throws ProductServiceException {
		double newPrice = roundTwoDecimals(p.getRetailPrice()*1.05);
		p.setRetailPrice(newPrice);
		try {
			dao.updateProduct(p);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not persist Product");
			throw new ProductServiceException("Could not persist product");
		}
	}
	
	protected double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}

	/* (non-Javadoc)
	 * @see services.ProductService#increasePriceByFivePercent(java.util.List)
	 */
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

	/* (non-Javadoc)
	 * @see services.ProductService#generateNewProduct()
	 */
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

	/* (non-Javadoc)
	 * @see services.ProductService#updateProduct(entities.Product)
	 */
	@Override
	public void updateProduct(Product p) throws ProductServiceException {
		try {
			dao.updateProduct(p);
		} catch (JDBCProductDAOImplException e) {
			logger.error("Could not persist Product");
			throw new ProductServiceException("Could not persist product");
		}
		
	}

	/* (non-Javadoc)
	 * @see services.ProductService#deleteProduct(entities.Product)
	 */
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
