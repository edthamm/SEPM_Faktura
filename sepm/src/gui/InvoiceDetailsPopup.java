package gui;

import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;

import entities.Consumption;
import entities.Invoice;

import services.InvoiceService;
import services.InvoiceServiceException;
import services.ProductService;

/**
 * The Class InvoiceDetailsPopup.
 */
public class InvoiceDetailsPopup  extends JOptionPane{

	private static final long serialVersionUID = 1600484530072069066L;
	private Logger logger = Logger.getLogger("gui.InvoiceDetailsPopup.class");
	private InvoiceService is;
	private ProductService ps;
	private JLabel idl;
	private JLabel id;
	private JLabel suml;
	private JLabel sum;
	private JLabel datel;
	private JLabel date;
	private JLabel timel;
	private JLabel time;
	private JLabel waiterl;
	private JLabel waiter;
	private Invoice i;
	private JTable consumptions;
	private JScrollPane consumptionsTablePane;
	private DefaultTableModel consumptionTableModel;
	
	/**
	 * Instantiates a new invoice details popup.
	 *
	 * @param is the InvoiceService
	 * @param ps the ProductService
	 */
	public InvoiceDetailsPopup(InvoiceService is, ProductService ps){
		super();
		this.is = is;
		this.ps = ps;
	}
	
	private void init(){
		
		setDisplayOptions();
		generateLabels();
		setLabels();
		initialiseTableModel();
		generateTable();
		fillTableWithData();
		removeAll();
		addEverytingToDisplay();
	}



	private void setDisplayOptions() {
		MigLayout l = new MigLayout();
		setLayout(l);
		
	}
	private void generateLabels() {
		idl = new JLabel("Rechnungsnummer");
		id = new JLabel();
		suml = new JLabel("Total");
		sum = new JLabel();
		datel = new JLabel("Datum");
		date = new JLabel();
		timel = new JLabel("Zeit");
		time = new JLabel();
		waiterl = new JLabel("Kellner");
		waiter = new JLabel();
		
	}
	
	private void setLabels(){
		id.setText(""+i.getId());
		sum.setText(""+i.getSum());
		date.setText(i.getDate().toString());
		time.setText(i.getTime().toString());
		waiter.setText(i.getWaiter());
	}
	
	private void initialiseTableModel(){
		createUneditableTableModel();
		setColumnNames();
	}

	@SuppressWarnings("serial")
	private void createUneditableTableModel() {
		consumptionTableModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private void setColumnNames() {
		Object[] columnNames = new Object[3];
		columnNames[0] = "Ware";
		columnNames[1] = "Menge";
		columnNames[2] = "Preis";

		
		consumptionTableModel.setColumnIdentifiers(columnNames);
	}
	
	private void generateTable() {
		consumptions = new JTable(consumptionTableModel);
		consumptionsTablePane = new JScrollPane(consumptions);
		
	}

	private void fillTableWithData() {
		Object[] newRow = new Object[3];
		Iterator<Consumption> consumptionIterator = i.getConsumptions().listIterator();
		while(consumptionIterator.hasNext()){
			Consumption c = consumptionIterator.next();
			newRow[0] = ps.getProductbyId(c.getProductID()).getLabel(); 
			newRow[1] = c.getQuantity();
			newRow[2] = c.getPrice();
			consumptionTableModel.addRow(newRow);
		}
		
	}

	private void addEverytingToDisplay() {
		logger.info("addy everything to display");
		add(idl);
		add(id,"wrap");
		add(suml);
		add(sum, "wrap");
		add(datel);
		add(date, "wrap");
		add(timel);
		add(time, "wrap");
		add(waiterl);
		add(waiter, "wrap");
		add(consumptionsTablePane, "span 2");
		
	}

	/**
	 * This method sets the invoice to be displayed in this popup.
	 *
	 * @param id the of the Invoice
	 */
	public void forInvoice(int id) {
		try {
			i = is.getInvoiceById(id);
		} catch (InvoiceServiceException e) {
			logger.error("Failed to retrieve Invoice.");
			JOptionPane.showMessageDialog(consumptionsTablePane, "Ihre Rechnung konte nicht aufgerugen werden.");
		}
		init();
		
	}
	
	

}
