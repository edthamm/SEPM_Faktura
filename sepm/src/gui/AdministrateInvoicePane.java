package gui;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import entities.Invoice;
import entities.Product;

public class AdministrateInvoicePane extends BasePane {

	private static final long serialVersionUID = 3523355764154359405L;
	private Logger logger = Logger.getLogger("gui.AdministrateInvoicePane.class");
	
	private JButton search;
	private JLabel inr;
	private JLabel datefrom;
	private JLabel datetill;
	private JLabel waiter;
	private JTextField inrField;
	private JTextField datefromField;
	private JTextField datetillField;
	private JTextField waiterField;
	private DefaultTableModel invoiceTableModel;
	private JScrollPane resultTablePane;
	private JTable results;
	
	public AdministrateInvoicePane(){
		super();
		
		createButtons();
		createLabels();
		createTextFields();
		initialiseTableModel();
		createResultPane();
		addEverythingToInterface();
		
	}

	private void createButtons() {
		search = new JButton("Suchen");
		
	}

	private void createLabels() {
		inr = new JLabel("Rechnungsnummer: ");
		datefrom = new JLabel("Rechnugsdatum von: ");
		datetill = new JLabel("bis: ");
		waiter = new JLabel("Kellner: ");
	}

	private void createTextFields() {
		inrField = new JTextField();
		datefromField = new JTextField("TT.MM.JJJJ");
		datetillField = new JTextField("TT.MM.JJJJ");
		waiterField = new JTextField();
	}	
	private void initialiseTableModel(){
		logger.info("initialising TableModel");
		createUneditableTableModel();
		setColumnNames();
	}

	@SuppressWarnings("serial")
	private void createUneditableTableModel() {
		invoiceTableModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private void setColumnNames() {
		Object[] columnNames = new Object[5];
		columnNames[0] = "Rechnungsnummer";
		columnNames[1] = "Summe";
		columnNames[2] = "Datum";
		columnNames[3] = "Uhrzeit";
		columnNames[4] = "Kellner";
		
		invoiceTableModel.setColumnIdentifiers(columnNames);
	}

	private void createResultPane() {
		logger.info("Creating ResultPane");
		results = new JTable();
		resultTablePane = new JScrollPane(results);
		
	}

	private void addEverythingToInterface() {
		JPanel wf = super.westField;
		JPanel eb = super.eastButtons;
		
		reconfigureButtonPanelLayout();
		
		eb.add(search);
		
		wf.add(inr);
		wf.add(inrField, "w 100, wrap");
		wf.add(datefrom);
		wf.add(datefromField,"w 100");
		wf.add(datetill);
		wf.add(datetillField,"w 100, wrap");
		wf.add(waiter);
		wf.add(waiterField,"w 300, span 3, wrap");
		wf.add(resultTablePane,"span 4, grow");
		
	}

	private void reconfigureButtonPanelLayout() {
		super.eastButtons.setLayout(new MigLayout("","grow",":push[]"));
	}
	
	public void updateResultsOfProductSearch(List<Invoice> invoices){
		logger.info("Updating Result Table");
		resetTableModel();
		if(invoices == null || invoices.isEmpty()){}
		else{
			fillTableWithNewEntries(invoices);
		}
		updateDisplayWithNewData();
	}

	private void resetTableModel() {
		invoiceTableModel.getDataVector().removeAllElements();
	}

	private void fillTableWithNewEntries(List<Invoice> invoices) {
		Object[] newRow = new Object[3];
		Iterator<Invoice> invoiceIterator = invoices.iterator();
		while(invoiceIterator.hasNext()){
			Invoice i = invoiceIterator.next();
			newRow[0] = i.getId(); 
			newRow[1] = i.getSum();
			//TODO check if to string is needed here
			newRow[2] = i.getDate();
			newRow[3] = i.getTime();
			newRow[4] = i.getWaiter();
			invoiceTableModel.addRow(newRow);
		}
	}
	
	private void updateDisplayWithNewData() {
		results.revalidate();
	}	


}
