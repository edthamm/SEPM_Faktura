package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

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
	
	private JScrollPane resultTablePane;
	private JTable results;
	
	public AdministrateInvoicePane(){
		super();
		
		createButtons();
		createLabels();
		createTextFields();
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

}
