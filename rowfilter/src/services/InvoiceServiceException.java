package services;

public class InvoiceServiceException extends Exception {

	private static final long serialVersionUID = 2346420714729272391L;
	
	public InvoiceServiceException(String s){
		super(s);
	}

	public InvoiceServiceException() {
		super();
	}

}
