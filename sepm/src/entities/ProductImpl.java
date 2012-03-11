package entities;

import org.apache.log4j.Logger;

public class ProductImpl implements Product{

	private int id = 0;
	private String label = "blank";
	private double purchasePrice = 0;
	private double retailPrice = 0;
	private String supplier = "blank";
	private boolean inSale = true;
	private Logger logger = Logger.getLogger("entities.ProductImpl.class");
	
	public ProductImpl(){
		logger.debug("Initializing default product");
	}
	
	
	public ProductImpl(String label, double purchasePrice, double retailPrice,
			String supplier, int id) {
		this.label = label;
		this.purchasePrice = purchasePrice;
		this.retailPrice = retailPrice;
		this.supplier = supplier;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public boolean isInSale() {
		return inSale;
	}

	public void setInSale(boolean inSale) {
		logger.info("inSale of"+this.label+" set to"+inSale);
		if(inSale){
			logger.warn("inSale of"+this.label+" id:"+this.id+"set to true via set.");
		}
		this.inSale = inSale;
	}
	
}
