package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;

import entities.Product;

import services.ProductService;

public class ProductDetailsPopup extends JOptionPane {

	private static final long serialVersionUID = -6004357805402889270L;
	private Logger logger = Logger.getLogger("gui.ProductDetailsPopup.class");
	private ProductService ps;
	private Product p;
	private JLabel pnrLabel;
	private JLabel pnr;
	private JLabel label;
	private JLabel pprice;
	private JLabel rprice;
	private JLabel supplier;
	private JTextField supplierField;
	private JTextField rpriceField;
	private JTextField ppriceField;
	private JTextField labelField;
	private JButton deleteButton;
	private JButton storeChangesButton;
	
	public ProductDetailsPopup(ProductService ps){
		super();
		this.ps = ps;
	}
	
	private void init(){
		
		setDisplayOptions();
		generateLabels();
		createFields();
		createButtons();
		setValues();
		removeAll();
		addEverythingToDisplay();
	}
	
	private void setDisplayOptions() {
		MigLayout l = new MigLayout();
		setLayout(l);
		
	}

	private void generateLabels() {
		pnrLabel = new JLabel("Produktnummer");
		pnr = new JLabel();
		label = new JLabel("Bezeichnung");
		pprice = new JLabel("Einkaufspreis");
		rprice = new JLabel("Verkaufspreis");
		supplier = new JLabel("Lieferant");
		
	}

	private void createFields() {
		labelField = new JTextField();
		ppriceField = new JTextField();
		rpriceField = new JTextField();
		supplierField = new JTextField();
	}

	private void createButtons() {
		deleteButton = new JButton("Produkt löschen");
		deleteButton.addActionListener(new deleteListener());
		storeChangesButton = new JButton("Änderungen übernehmen");
		storeChangesButton.addActionListener(new storeChangeListener());
		
	}
	
	private void setValues(){
		pnr.setText(""+p.getId());
		labelField.setText(p.getLabel());
		ppriceField.setText(""+p.getPurchasePrice());
		rpriceField.setText(""+p.getRetailPrice());
		supplierField.setText(p.getSupplier());
	}

	private void addEverythingToDisplay() {
		add(pnrLabel);
		add(pnr,"wrap");
		add(label);
		add(labelField,"wrap");
		add(pprice);
		add(ppriceField,"wrap");
		add(rprice);
		add(rpriceField,"wrap");
		add(supplier);
		add(supplierField,"wrap");
		add(deleteButton);
		add(storeChangesButton);
	}

	public void forProduct(Product t){
		p = ps.getProductbyId(t.getId());
		init();
	}

	public void forProduct(int id) {
		p = ps.getProductbyId(id);
		init();
	}
	
	private class storeChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateProduct();
			storeProductToDb();
			
		}

		private void updateProduct() {
			try{
				p.setPurchasePrice(Double.parseDouble(ppriceField.getText()));
				p.setRetailPrice(Double.parseDouble(rpriceField.getText()));
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Bitte überprüfen Sie die Preisfelder auf Fehler");
				return;
			}
			p.setSupplier(supplierField.getText());
			p.setLabel(labelField.getText());
			
		}

		private void storeProductToDb() {
			ps.updateProduct(p);
			
		}
		
	}
	
	private class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int respone = JOptionPane.showConfirmDialog(null, "Dieses Produkt wirklich Löschen?", "Bitte bestätigen" , JOptionPane.YES_NO_OPTION);
			if(respone == JOptionPane.YES_OPTION){
				logger.debug("Deleting Product now");
				ps.deleteProduct(p);
				//TODO close pane
			}
		}
		
	}
}
