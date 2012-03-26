package gui;


import net.miginfocom.swing.MigLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

import dao.DatabaseConnector;
import dao.DatabaseConnectorImpl;
import dao.InvoiceDAO;
import dao.JDBCInvoiceDAOImpl;
import dao.JDBCInvoiceDAOImplException;
import dao.JDBCProductDAOImpl;
import dao.JDBCProductDAOImplException;

import services.InvoiceService;
import services.InvoiceServiceImpl;
import services.ProductService;
import services.ProductServiceImpl;
import services.StatisticService;
import services.StatisticServiceImpl;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 * The Class MainFrame.
 */
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 4714864527745266449L;
	private Logger logger = Logger.getLogger("gui.MainFrame.class");
	private DatabaseConnector dbc = new DatabaseConnectorImpl("productiondb.properties");
	private InvoiceDAO idao;
	private InvoiceService is;
	private ProductService ps;
	

	private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
	private JDBCProductDAOImpl pdao;
	private StatisticService stats;
	
	/**
	 * Instantiates a new main frame.
	 *
	 * @param title the title
	 */
	public MainFrame(String title){
		super(title);
		
		handleAllTheSetup();
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
		tabs.addTab("Rechnungen verwalten", new AdministrateInvoicePane(is, ps));
		tabs.addTab("Artikel verwalten", new AdministrateProductsPane(ps, stats));
		tabs.addTab("Benutzer verwalten", new AdministrateUserPane(is));
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

	private void handleAllTheSetup(){
		try {
			pdao = new JDBCProductDAOImpl(dbc);
			ps = new ProductServiceImpl(pdao);
			idao = new JDBCInvoiceDAOImpl(dbc);
			is = new InvoiceServiceImpl(idao, ps);
			stats = new StatisticServiceImpl(ps, is);
		} catch (JDBCInvoiceDAOImplException e) {
		} catch (JDBCProductDAOImplException e) {
			JOptionPane.showMessageDialog(null, "Es konnte keine Datenbankverbindung aufgebaut werden. Das Programm wird geschlossen.");
			logger.fatal("Could not connect to persistence Layer terminating");
			System.exit(1);
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args will be ignored
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception e){
					System.out.println("Could not aquire nativie Look&Feel");
				}
				MainFrame frame = new MainFrame("Faktura 2012");
				frame.setVisible(true);
			}
		});
	}
}
