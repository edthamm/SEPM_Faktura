package gui;

import javax.swing.JOptionPane;

import entities.Product;

import services.ProductService;

public class ProductDetailsPopup extends JOptionPane {

	private static final long serialVersionUID = -6004357805402889270L;
	private ProductService ps;
	private Product p;
	
	public ProductDetailsPopup(ProductService ps){
		super();
		this.ps = ps;
	}
	
	private void init(){
		
		
	}
	
	public void forProduct(Product t){
		p = ps.getProductbyId(t.getId());
		init();
	}

	public void forProduct(int id) {
		// TODO Auto-generated method stub
		
	}
}
