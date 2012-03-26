package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import services.InvoiceService;


/**
 * The Class AdministrateUserPane.
 */
public class AdministrateUserPane extends BasePane{

	private static final long serialVersionUID = -9021971906293031516L;
	private Logger logger = Logger.getLogger("gui.AdministrateUserPane.class");
	private InvoiceService is;
	private JButton confirmUser;
	private JLabel username;
	private JTextField usernameTextField;
	
	/**
	 * Instantiates a new administrate user pane.
	 *
	 * @param is the InvoiceService
	 */
	public AdministrateUserPane(InvoiceService is){
		super();
		this.is = is;
		setUpButtons();
		setUpField();
		adjustLayoutOfEastButtonsForJustOneButton();
		addEverythingToTheInterface();
		
	}
	private void setUpButtons() {
		logger.info("Setting up buttons");
		confirmUser = new JButton("<html>Benutzer<br>Übernehmen</html>");
		confirmUser.addActionListener(new usernameChangeListener());
	}	
	
	private void setUpField() {
		logger.info("Setting up fields");
		username = new JLabel("Benutzername:");
		usernameTextField = new JTextField("Bitte geben Sie hier Ihren Benutzernamen ein");
	}
	
	private void adjustLayoutOfEastButtonsForJustOneButton(){
		logger.info("Adjusting Layout");
		super.eastButtons.setLayout(new MigLayout("","grow",":push[]"));
	}
	
	private void addEverythingToTheInterface() {
		logger.info("Adding everything to interface");
		super.eastButtons.add(confirmUser);
		super.westField.add(username);
		super.westField.add(usernameTextField, "wrap, wmin 300");
		
	}
	
	private class usernameChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = usernameTextField.getText();
			is.setWaiter(name);
		}
		
	}




}
