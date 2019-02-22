package it.taglio.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JMenuItem;

public class GuiMenuItem<T extends UDNDialog> extends JMenuItem implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private final UDNGui frame;
	private final Class<T> obj;
	
	public GuiMenuItem(String name, UDNGui frame, Class<T> obj) {
		super(name);
		this.frame = frame;
		this.obj = obj;
		
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			obj.getConstructor(UDNGui.class).newInstance(frame);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

}
