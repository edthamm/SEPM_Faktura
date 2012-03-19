package gui;


import net.miginfocom.swing.MigLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

import services.InvoiceService;
import services.ProductService;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 4714864527745266449L;
	private Logger logger = Logger.getLogger("gui.MainFrame.class");
	private InvoiceService is;
	private ProductService ps;
	

	private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
	
	public MainFrame(String title){
		super(title);
		
		setLayoutOfMainFrame();
		addTabs();
		makeTabsVisible();
		setDefaultBehavior();
		
	}

	private void setLayoutOfMainFrame() {
		logger.info("Setting Layout");
		MigLayout layout = new MigLayout();
		setLayout(layout);
	}
	
	private void addTabs() {
		logger.info("Adding tabs");
		tabs.addTab("Rechnung erstellen", new NewInvoicePane(is,ps));
		tabs.addTab("Rechnungen verwalten", new AdministrateInvoicePane());
		tabs.addTab("Artikel verwalten", new AdministrateProductsPane());
		tabs.addTab("Benutzer verwalten", new AdministrateUserPane());
	}
	
	private void makeTabsVisible() {
		logger.info("Making tabs visible");
		add(tabs,"grow, push");
	}
	
	private void setDefaultBehavior() {
		logger.info("Setting default behavior");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private static void handleAllTheSetup(){
		
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		handleAllTheSetup();
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception e){
					//TODO
					e.printStackTrace();
				}
				MainFrame frame = new MainFrame("Faktura 2012");
				frame.setVisible(true);
			}
		});

	}

}
