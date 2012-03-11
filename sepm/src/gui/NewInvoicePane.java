package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;


public class NewInvoicePane extends BasePane{

	private static final long serialVersionUID = 4539993258343834799L;
	private Logger logger = Logger.getLogger("gui.NewInvoicePane.class");
	private JButton newInvoice;
	private JButton addProduct;
	private JButton closeInvoice;
	private JComboBox openInvoices;
	private JLabel pnr;
	private JLabel pname;
	private JLabel qty;
	private JTextField pnrField;
	private JTextField pnameField;
	private JTextField qtyField;
	private JScrollPane resultTablePane;
	private JTable results;
	
	public NewInvoicePane(){
		super();
		
		createButtons();
		createDropDown();
		createLabels();
		createTextFields();
		createResultPane();
		addEverythingToInterface();
	}

	private void createButtons() {
		newInvoice = new JButton("<html>Neue<br>Rechnung</html>");
		addProduct = new JButton("Hinzufügen");
		closeInvoice = new JButton("<html>Rechnung<br>abschließen<html>");
		
	}
	
	private void createDropDown(){
		openInvoices = new JComboBox();
	}

	private void createLabels() {
		pnr = new JLabel("Artikelnummer: ");
		pname = new JLabel("Artikelbezeichnung: ");
		qty = new JLabel("Anzahl: ");
		
	}

	private void createTextFields() {
		pnrField = new JTextField();
		pnameField = new JTextField();
		qtyField = new JTextField();
		
	}

	private void createResultPane() {
		results = new JTable();
		resultTablePane = new JScrollPane(results);
		
	}

	private void addEverythingToInterface() {
		JPanel wf = super.westField;
		JPanel eb = super.eastButtons;
		
		eb.add(newInvoice,"wrap");
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
		
	}
	
	public void updateResultsOfProductSearch(){
		
	}
	

}
