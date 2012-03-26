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
import services.ProductServiceException;

/**
 * The Class ProductDetailsPopup.
 */
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
	
	/**
	 * Instantiates a new product details popup.
	 *
	 * @param ps the ProductService
	 */
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
		deleteButton.addActionListener(new deleteListener(this));
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
		add(labelField,"wrap, wmin 100");
		add(pprice);
		add(ppriceField,"wrap, wmin 100");
		add(rprice);
		add(rpriceField,"wrap, wmin 100");
		add(supplier);
		add(supplierField,"wrap, wmin 100");
		add(deleteButton);
		add(storeChangesButton);
	}

	/**
	 * For product.
	 *
	 * @param t the product to be displayed
	 */
	public void forProduct(Product t){
		p = ps.getProductbyId(t.getId());
		init();
	}

	/**
	 * For product.
	 *
	 * @param id the id of the product to be displayed
	 */
	public void forProduct(int id) {
		p = ps.getProductbyId(id);
		init();
	}
	
	private class storeChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				updateProduct();
			}
			catch(IllegalArgumentException e){
				return;
			}
			storeProductToDb();
			JOptionPane.showMessageDialog(null, "Änderungen übernommen");
			
		}

		private void updateProduct() throws IllegalArgumentException{
			try{
				p.setPurchasePrice(Double.parseDouble(ppriceField.getText()));
				p.setRetailPrice(Double.parseDouble(rpriceField.getText()));
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Bitte überprüfen Sie die Preisfelder auf Fehler");
				throw new IllegalArgumentException();
			}
			p.setSupplier(supplierField.getText());
			p.setLabel(labelField.getText());
			
		}

		private void storeProductToDb() {
			try {
				ps.updateProduct(p);
			} catch (ProductServiceException e) {
				logger.error("Could not update Product");
				JOptionPane.showMessageDialog(null, "Scheinbar gibt es ein Datenbank Problem. Bitte mal nen Techniker holen");
			}
			
		}
		
	}
	
	private class deleteListener implements ActionListener{
		
		private ProductDetailsPopup popup;
		
		private deleteListener(ProductDetailsPopup popup){
			this.popup = popup;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int respone = JOptionPane.showConfirmDialog(null, "Dieses Produkt wirklich Löschen?", "Bitte bestätigen" , JOptionPane.YES_NO_OPTION);
			if(respone == JOptionPane.YES_OPTION){
				logger.debug("Deleting Product now");
				try {
					ps.deleteProduct(p);
				} catch (ProductServiceException e1) {
					logger.error("Could not delete Product");
					JOptionPane.showMessageDialog(null, "Scheinbar gibt es ein Datenbank Problem. Bitte mal nen Techniker holen");
				}
				popup.setVisible(false);
			}
		}
		
	}
}
