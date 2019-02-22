package it.taglio.listeners;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import it.taglio.Main;
import it.taglio.gui.UDNGui;

public class AppAdapter implements DropTargetListener, WindowListener, OptionsUpdateListener {

	private final UDNGui frame;

	public AppAdapter(UDNGui frame) {
		this.frame = frame;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent event) {
		try {
			event.acceptDrop(DnDConstants.ACTION_COPY);
			List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

			int i = 0;
			while (i < files.size() && !files.get(i).getName().substring(files.get(i).getName().lastIndexOf('.'))
					.equalsIgnoreCase(".dll"))
				++i;

			if (i < files.size())
				frame.setDLL(files.get(i));
			else
				JOptionPane.showMessageDialog(null, "No DLL file found.", "Failure", JOptionPane.WARNING_MESSAGE);
		} catch (UnsupportedFlavorException | IOException e) {
			JOptionPane.showMessageDialog(null, "Can't import the dragged file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowClosing(WindowEvent event) {
		if ( event.getID() == WindowEvent.WINDOW_CLOSING)
			Main.saveRecent();
	}

	@Override
	public void updateOptions(OptionsUpdateEvent event) {
		
	}


	@Override
	public void dropActionChanged(DropTargetDragEvent drag) {
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	@Override
	public void dragEnter(DropTargetDragEvent drag) {
	}

	@Override
	public void dragExit(DropTargetEvent event) {
	}

	@Override
	public void dragOver(DropTargetDragEvent drag) {
	}
}
