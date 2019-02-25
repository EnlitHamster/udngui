package it.taglio.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

public class FileMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = -1758046441421626887L;
	
	private final File item;
	private final UDNGui frame;
	
	public FileMenuItem(UDNGui frame, File item) {
		this.item = item;
		this.frame = frame;
		
		setText(item.getName());
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		frame.setDLL(item);
	}
	
}
