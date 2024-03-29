package entities;

/**
 * The Class Consumption.
 */
public class Consumption {

	private int productID;
	private int quantity;
	private double price;
	
	/**
	 * Instantiates a new consumption.
	 *
	 * @param productID the product id
	 * @param quantity the quantity consumed
	 * @param price the price at the time of sale
	 */
	public Consumption(int productID, int quantity, double price) {
		this.productID = productID;
		this.quantity = quantity;
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public String toString(){
		return "Consumption "+productID+" "+quantity+" "+price;
	}
	
}
