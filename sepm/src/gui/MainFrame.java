package gui;


import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 4714864527745266449L;
	private Logger logger = Logger.getLogger("gui.MainFrame.class");

	private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
	
	public MainFrame(String title){
		super(title);
		
		logger.info("Setting Layout");
		MigLayout layout = new MigLayout();
		setLayout(layout);
		
		logger.info("Adding tabs");
		tabs.addTab("Rechnung erstellen", new JPanel());
		tabs.addTab("Rechnungen verwalten", new JPanel());
		tabs.addTab("Artikel verwalten", new JPanel());
		tabs.addTab("Benutzer verwalten", new JPanel());
		
		logger.info("Making tabs visible");
		add(tabs,"grow, push");
		
		logger.info("Setting default behavior");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception e){
					//TODO
					e.printStackTrace();
				}
				MainFrame frame = new MainFrame("Edis Gui");
				frame.setVisible(true);
			}
		});

	}

}
