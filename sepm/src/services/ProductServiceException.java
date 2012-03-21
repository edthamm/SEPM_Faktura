package services;

public class ProductServiceException extends Exception {

	private static final long serialVersionUID = -2497411398143603309L;
	
	public ProductServiceException(String s){
		super(s);
	}

	public ProductServiceException() {
		super();
	}
}
