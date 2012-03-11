package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;


public class NewInvoicePane extends BasePane{

	private static final long serialVersionUID = 4539993258343834799L;
	private Logger logger = Logger.getLogger("gui.NewInvoicePane.class");
	private JButton newInvoice;
	private JButton addProduct;
	private JButton closeInvoice;
	private JLabel pnr;
	private JLabel pname;
	private JLabel qty;
	private JTextField pnrField;
	private JTextField pnameField;
	private JTextField qtyField;
	
	public NewInvoicePane(){
		super();
		
		createButtons();
		createLabels();
		createTextFields();
		createResultFrame();
		addEverythingToInterface();
	}

	private void createButtons() {
		newInvoice = new JButton("<html>Neue<br>Rechnung</html>");
		addProduct = new JButton("Hinzufügen");
		closeInvoice = new JButton("<html>Rechnung<br>abschließen<html>");
		
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

	private void createResultFrame() {
		// TODO Auto-generated method stub
		
	}

	private void addEverythingToInterface() {
		JPanel wf = super.westField;
		JPanel eb = super.eastButtons;
		
		eb.add(newInvoice,"wrap,");
		eb.add(closeInvoice,"wrap, south");
		eb.add(addProduct,"south");
		
		//add dropdown
		wf.add(pnr);
		wf.add(pnrField,"wrap");
		wf.add(pname);
		wf.add(pnameField,"wrap");
		//add result window
		wf.add(qty);
		wf.add(qtyField);
	}
	

}
