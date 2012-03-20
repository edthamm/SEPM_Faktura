package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import services.ProductService;
import services.StatisticService;

import entities.Product;

public class AdministrateProductsPane extends BasePane {

	private static final long serialVersionUID = -5874966514832873507L;
	private Logger logger = Logger.getLogger("gui.AdministrateProductsPane.class");
	private ProductService ps;
	private StatisticService stats;
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
	private DefaultTableModel productTableModel;
	private JScrollPane resultTablePane;
	private JTable results;
	private TableRowSorter<TableModel> sorter;
	
	public AdministrateProductsPane(ProductService ps, StatisticService stats){
		super();
		this.ps = ps;
		this.stats = stats;
		
		createButtons();
		createDropDowns();
		createLabels();
		createTextFields();
		initialiseTableModel();
		createResultPane();
		addEverythingToInterface();
		
	}

	private void createButtons() {
		logger.info("Creating Buttons");
		newProduct = new JButton("<html>Neuer<br>Artikel</html>");
		search = new JButton("Suchen");
		search.addActionListener(new searchListener());
		increasePriceOfTopsellers = new JButton("Preis der Top 3 um 5% erhöhen");
		showTopsellers = new JButton("Top 3 der letzten 30 Tage anzeigen");
		
	}

	private void createDropDowns() {
		logger.info("Creating DropDowns");
		String [] relation = {"=","<",">"};
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
		Object[] columnNames = new Object[5];
		columnNames[0] = "Waren ID";
		columnNames[1] = "Bezeichnung";
		columnNames[2] = "Preis - Verkauf";
		columnNames[3] = "Preis - Einkauf";
		columnNames[4] = "Lieferant";
		
		productTableModel.setColumnIdentifiers(columnNames);
	}

	private void createResultPane() {
		logger.info("Creating ResultPane");
		results = new JTable(productTableModel);
		resultTablePane = new JScrollPane(results);
		sorter = new TableRowSorter<TableModel>(results.getModel());
		results.setRowSorter(sorter);
	}

	private void addEverythingToInterface() {
		logger.info("Adding everything together");
		JPanel wf = super.westField;
		JPanel eb = super.eastButtons;
		
		eb.add(newProduct,"wrap, grow");
		eb.add(search,"span 2 3, grow");
		
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
		wf.add(showTopsellers,"span 2,grow");
		wf.add(increasePriceOfTopsellers,"span 3, grow");
		
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
		Object[] newRow = new Object[5];
		Iterator<Product> productIterator = products.iterator();
		while(productIterator.hasNext()){
			Product p = productIterator.next();
			newRow[0] = p.getId(); 
			newRow[1] = p.getLabel();
			newRow[2] = p.getRetailPrice();
			newRow[3] = p.getPurchasePrice();
			newRow[4] = p.getSupplier();
			productTableModel.addRow(newRow);
		}
	}
	
	private void updateDisplayWithNewData() {
		results.revalidate();
	}	

	private class searchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			filterTable();
		}
		private void filterTable() {
			RowFilter<? super TableModel, Object> rf = createFilter();
			if(rf == null){
				sorter.setRowFilter(null);
				fillTableWithAllProducts();
				return;
			}
			sorter.setRowFilter(rf);
		}
		
		private RowFilter<? super TableModel, Object> createFilter() {
			List<RowFilter<? super TableModel, Object>> filters = new LinkedList<RowFilter<? super TableModel, Object>>();
			filters = createAndAddSubfilters(filters);
			if(filters.isEmpty()){return null;}
			return RowFilter.andFilter(filters);
		}
		
		private List<RowFilter<? super TableModel, Object>> createAndAddSubfilters(
				List<RowFilter<? super TableModel, Object>> filters) {
			filters.add(createIdFilter());
			filters.add(createLabelFilter());
			filters.add(createSupplierFilter());
			filters.add(createPriceFilters());
			
			filters.removeAll(Collections.singletonList(null));
			
			return filters;
			
		}
		private RowFilter<? super TableModel, Object> createIdFilter() {
			// TODO Auto-generated method stub
			return null;
		}
		private RowFilter<? super TableModel, Object> createLabelFilter() {
			// TODO Auto-generated method stub
			return null;
		}
		private RowFilter<? super TableModel, Object> createSupplierFilter() {
			// TODO Auto-generated method stub
			return null;
		}
		private RowFilter<? super TableModel, Object> createPriceFilters() {
			// TODO Auto-generated method stub
			return null;
		}
		private void fillTableWithAllProducts() {
			List<Product> result = ps.getAllProducts();
			updateResultsOfProductSearch(result);
		}
		
	}

}
