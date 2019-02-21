package it.taglio.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import it.taglio.gui.UDNGui;

public class FileChooserListener implements PropertyChangeListener {

	private final UDNGui frame;

	public FileChooserListener(UDNGui frame) {
		this.frame = frame;
	}

	@Override
	public void propertyChange(PropertyChangeEvent selection) {
		if (selection.getNewValue() != null) {
			String file = selection.getNewValue().toString();
			if (file.lastIndexOf('.') != -1 && (file.substring(file.lastIndexOf('.')).equalsIgnoreCase(".dll")))
				frame.updateTree(file);
		}
	}

}
