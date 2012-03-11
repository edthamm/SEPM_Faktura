package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;


public class AdministrateUserPane extends BasePane{

	private static final long serialVersionUID = -9021971906293031516L;
	private Logger logger = Logger.getLogger("gui.AdministrateUserPane.class");
	private JButton confirmUser;
	private JLabel username;
	private JTextField usernameTextField;
	
	public AdministrateUserPane(){
		super();
		
		setUpButtons();
		setUpField();
		addEverythingToTheInterface();
		
	}
	private void setUpButtons() {
		confirmUser = new JButton("<html>Benutzer<br>Übernehmen</html>");
	}	
	
	private void setUpField() {
		username = new JLabel("Benutzername:");
		usernameTextField = new JTextField("Bitte geben Sie hier Ihren Benutzernamen ein");
	}
	
	private void addEverythingToTheInterface() {
		super.eastButtons.add(confirmUser);
		super.westField.add(username);
		super.westField.add(usernameTextField, "wrap");
		
	}




}
