package gui;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import entities.Product;


public class NewInvoicePane extends BasePane{

	private static final long serialVersionUID = 4539993258343834799L;
	private Logger logger = Logger.getLogger("gui.NewInvoicePane.class");
	private JButton newInvoice;
	private JButton addProduct;
	private JButton closeInvoice;
	private JButton search;
	private JComboBox openInvoices;
	private JLabel pnr;
	private JLabel pname;
	private JLabel qty;
	private JTextField pnrField;
	private JTextField pnameField;
	private JTextField qtyField;
	private DefaultTableModel productTableModel;
	private JScrollPane resultTablePane;
	private JTable results;
	
	public NewInvoicePane(){
		super();
		
		createButtons();
		createDropDown();
		createLabels();
		createTextFields();
		initialiseTableModel();
		createResultPane();
		addEverythingToInterface();
	}

	private void createButtons() {
		logger.info("Creating Buttons");
		newInvoice = new JButton("<html>Neue<br>Rechnung</html>");
		addProduct = new JButton("Hinzufügen");
		closeInvoice = new JButton("<html>Rechnung<br>abschließen<html>");
		search = new JButton("Suchen");
		
	}
	
	private void createDropDown(){
		logger.info("Creating Dropdown");
		openInvoices = new JComboBox();
	}

	private void createLabels() {
		logger.info("Creating Labels");
		pnr = new JLabel("Artikelnummer: ");
		pname = new JLabel("Artikelbezeichnung: ");
		qty = new JLabel("Anzahl: ");
		
	}

	private void createTextFields() {
		logger.info("Creating TextFields");
		pnrField = new JTextField();
		pnameField = new JTextField();
		qtyField = new JTextField();
		
	}
	
	private void initialiseTableModel(){
		logger.info("initialising TableModel");
		createUneditableTableModel();
		setColumnNames();
	}

	@SuppressWarnings("serial")
	private void createUneditableTableModel() {
		productTableModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private void setColumnNames() {
		Object[] columnNames = new Object[3];
		columnNames[0] = "Waren ID";
		columnNames[1] = "Bezeichnung";
		columnNames[2] = "Preis";
		productTableModel.setColumnIdentifiers(columnNames);
	}

	private void createResultPane() {
		logger.info("Creating ResultPane");
		results = new JTable(productTableModel);
		resultTablePane = new JScrollPane(results);
		
	}

	private void addEverythingToInterface() {
		logger.info("Adding everything to the Interface");
		JPanel wf = super.westField;
		JPanel eb = super.eastButtons;
		
		eb.add(newInvoice,"wrap, grow");
		eb.add(search,"wrap, gapbottom 730, grow");
		eb.add(closeInvoice,"wrap, south");
		eb.add(addProduct,"south");
		
		wf.add(openInvoices,"wrap, wmin 300, span 2");
		
		wf.add(pnr);
		wf.add(pnrField,"wrap, w 100");
		wf.add(pname);
		wf.add(pnameField,"wrap,w 500");
		
		wf.add(resultTablePane, "span 2, grow");
		
		wf.add(qty,"cell 1 4,split , right");
		wf.add(qtyField,"right, w 100");
	}
	
	public void addInvoiceToOpenInvoices(){
		logger.info("Adding to open Invoices");
	}
	
	
	public void updateResultsOfProductSearch(List<Product> products){
		logger.info("Updating Result Table");
		resetTableModel();
		if(products == null || products.isEmpty()){}
		else{
			fillTableWithNewEntries(products);
		}
		updateDisplayWithNewData();
	}

	private void resetTableModel() {
		productTableModel.getDataVector().removeAllElements();
	}

	private void fillTableWithNewEntries(List<Product> products) {
		Object[] newRow = new Object[3];
		Iterator<Product> productIterator = products.iterator();
		while(productIterator.hasNext()){
			Product p = productIterator.next();
			newRow[0] = p.getId(); 
			newRow[1] = p.getLabel();
			newRow[2] = p.getRetailPrice();
			productTableModel.addRow(newRow);
		}
	}
	
	private void updateDisplayWithNewData() {
		results.revalidate();
	}	

}
