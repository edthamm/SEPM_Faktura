package entities;

public interface Product {
	public int getId() ;	
	public String getLabel() ;
	public void setLabel(String label) ;	
	public double getPurchasePrice() ;
	public void setPurchasePrice(double purchasePrice) ;	
	public double getRetailPrice() ;
	public void setRetailPrice(double retailPrice) ;	
	public String getSupplier() ;
	public void setSupplier(String supplier) ;
	public boolean isInSale() ;
	public void setInSale(boolean inSale) ;
}
