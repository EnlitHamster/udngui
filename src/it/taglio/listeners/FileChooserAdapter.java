package it.taglio.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;

import it.taglio.gui.UDNGui;

public class FileChooserAdapter implements PropertyChangeListener, ActionListener {

	private final UDNGui frame;

	public FileChooserAdapter(UDNGui frame) {
		this.frame = frame;
	}

	@Override
	public void propertyChange(PropertyChangeEvent selection) {
		if (selection.getNewValue() != null) {
			String file = selection.getNewValue().toString();
			if (file.lastIndexOf('.') != -1 && (file.substring(file.lastIndexOf('.')).equalsIgnoreCase(".dll"))) {
				frame.updateRecent(new File(file));
				frame.updateTree(file);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
			frame.setDLL(new File(""));
			frame.updateTree(null);
		}
	}

}
