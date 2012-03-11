package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class AdministrateProductsPane extends BasePane {

	private static final long serialVersionUID = -5874966514832873507L;
	private Logger logger = Logger.getLogger("gui.AdministrateProductsPane.class");
	
	private JButton newProduct;
	private JButton search;
	private JButton increasePriceOfTopsellers;
	private JButton showTopsellers;
	private JComboBox purchaseRelation;
	private JComboBox retailRelation;
	private JLabel pnr;
	private JLabel pname;
	private JLabel supplier;
	private JLabel pprice;
	private JLabel rprice;
	private JTextField pnrField;
	private JTextField pnameField;
	private JTextField supplierField;
	private JTextField ppriceField;
	private JTextField rpriceField;
	private JScrollPane resultTablePane;
	private JTable results;
	
	public AdministrateProductsPane(){
		super();
		
		createButtons();
		createDropDowns();
		createLabels();
		createTextFields();
		createResultPane();
		addEverythingToInterface();
		
	}

	private void createButtons() {
		logger.info("Creating Buttons");
		newProduct = new JButton("<html>Neuer<br>Artikel</html>");
		search = new JButton("Suchen");
		increasePriceOfTopsellers = new JButton("Preis der Top 3 um 5% erhöhen");
		showTopsellers = new JButton("Top 3 der letzten 30 Tage anzeigen");
		
	}

	private void createDropDowns() {
		logger.info("Creating DropDowns");
		String [] relation = {"=","<",">","<=",">="};
		purchaseRelation = new JComboBox(relation);
		retailRelation = new JComboBox(relation);
	}

	private void createLabels() {
		logger.info("Creating Labels");
		pnr = new JLabel("Artikelnummer: ");
		pname = new JLabel("Artikelbezeichnung: ");
		supplier = new JLabel("Lieferant: ");
		pprice = new JLabel("Einkaufspreis: ");
		rprice = new JLabel("Verkaufspreis: ");
		
	}

	private void createTextFields() {
		logger.info("Creating TextFields");
		pnrField = new JTextField();
		pnameField = new JTextField();
		supplierField = new JTextField();
		ppriceField = new JTextField();
		rpriceField = new JTextField();
	}

	private void createResultPane() {
		logger.info("Creating ResultPane");
		results = new JTable();
		resultTablePane = new JScrollPane(results);
		
	}

	private void addEverythingToInterface() {
		logger.info("Adding everything together");
		JPanel wf = super.westField;
		JPanel eb = super.eastButtons;
		
		eb.add(newProduct,"wrap");
		eb.add(search);
		
		wf.add(pnr);
		wf.add(pnrField,"w 100, wrap");
		wf.add(pname);
		wf.add(pnameField,"w 300");
		wf.add(pprice);
		wf.add(purchaseRelation);
		wf.add(ppriceField,"w 100, wrap");
		wf.add(supplier);
		wf.add(supplierField,"w 300");
		wf.add(rprice);
		wf.add(retailRelation);
		wf.add(rpriceField,"w 100, wrap");
		wf.add(resultTablePane,"span 5,grow, wrap");
		wf.add(showTopsellers,"span 2");
		wf.add(increasePriceOfTopsellers,"span 2, grow");
		
	}

}
