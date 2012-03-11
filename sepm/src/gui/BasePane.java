package gui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class BasePane extends JPanel{

	private static final long serialVersionUID = 4932461605479054032L;
	protected JPanel southButtons = new JPanel();
	
	public BasePane(){
		super();
		
		MigLayout layout = new MigLayout();
		setLayout(layout);
		
		MigLayout southLayout = new MigLayout();
		southButtons.setLayout(southLayout);
		
		add(southButtons,"dock south");
		
		
	}

}
