package gui;

//TODO should enter be used to accept form values

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
		
		MigLayout eastLayout = new MigLayout("","grow","[]:push[]");
		eastButtons.setLayout(eastLayout);
		
		MigLayout westLayout = new MigLayout("","grow","");
		westField.setLayout(westLayout);
		
		add(westField, "dock west, w max");
		add(eastButtons,"dock east");
		
		
	}

}
