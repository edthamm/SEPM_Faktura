package gui;

import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

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
		
		setDisplayOptions();
		generateLabels();
		createFields();
		createButtons();
		removeAll();
		addEverythingToDisplay();
	}
	
	private void setDisplayOptions() {
		MigLayout l = new MigLayout();
		setLayout(l);
		
	}

	private void generateLabels() {
		// TODO Auto-generated method stub
		
	}

	private void createFields() {
		// TODO Auto-generated method stub
		
	}

	private void createButtons() {
		// TODO Auto-generated method stub
		
	}

	private void addEverythingToDisplay() {
		// TODO Auto-generated method stub
		
	}

	public void forProduct(Product t){
		p = ps.getProductbyId(t.getId());
		init();
	}

	public void forProduct(int id) {
		p = ps.getProductbyId(id);
		init();
	}
}
