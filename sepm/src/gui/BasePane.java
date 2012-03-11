package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class BasePane extends JPanel{

	private static final long serialVersionUID = 4932461605479054032L;
	protected JPanel eastButtons = new JPanel();
	protected JPanel westField = new JPanel();
	
	public BasePane(){
		super();
		
		MigLayout layout = new MigLayout("fill","[]","");
		setLayout(layout);
		
		MigLayout southLayout = new MigLayout("","grow","[]:push[]");
		eastButtons.setLayout(southLayout);
		
		eastButtons.setBackground(Color.BLACK);
		westField.setBackground(Color.BLUE);
		add(westField, "dock west");
		add(eastButtons,"dock east");
		
		
	}

}
