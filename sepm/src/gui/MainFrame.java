package gui;

import org.apache.log4j.Logger;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.UIManager;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 4714864527745266449L;
	private Logger logger = Logger.getLogger("gui.MainFrame.class");

	private JDesktopPane mypane = new JDesktopPane();
	private JList list = new JList();
	
	public MainFrame(String title){
		super(title);
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
