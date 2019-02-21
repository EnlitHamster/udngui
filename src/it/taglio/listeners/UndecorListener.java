package it.taglio.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.taglio.gui.UDNGui;

public class UndecorListener implements ActionListener {
	
	private final UDNGui frame;
	
	public UndecorListener(UDNGui frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		frame.undecorate();
	}

}
