package gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

import entities.Invoice;

import services.InvoiceService;
import services.ProductService;

public class InvoiceDetailsPopup  extends JOptionPane{

	private static final long serialVersionUID = 1600484530072069066L;
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
	
	public InvoiceDetailsPopup(InvoiceService is, ProductService ps){
		super();
		this.is = is;
		this.ps = ps;
	}
	
	private void init(){
		
		setDisplayOptions();
		generateLabels();
		setLabels();
		generateTable();
		fillTableWithData();
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
	
	private void generateTable() {
		// TODO Auto-generated method stub
		
	}

	private void fillTableWithData() {
		// TODO Auto-generated method stub
		
	}

	private void addEverytingToDisplay() {
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
		
	}

	public void forInvoice(int id) {
		i = is.getInvoiceById(id);
		init();
		
	}
	
	

}
