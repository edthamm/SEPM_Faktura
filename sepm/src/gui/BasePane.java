package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class BasePane extends JPanel{

	private static final long serialVersionUID = 4932461605479054032L;
	protected JPanel eastButtons = new JPanel();
	protected JPanel westPanel = new JPanel();
	
	public BasePane(){
		super();
		
		MigLayout layout = new MigLayout("fill","[]","");
		setLayout(layout);
		
		MigLayout southLayout = new MigLayout();
		eastButtons.setLayout(southLayout);
		
		eastButtons.setBackground(Color.BLACK);
		westPanel.setBackground(Color.BLUE);
		add(westPanel, "dock west");
		add(eastButtons,"dock east");
		
		
	}

}
