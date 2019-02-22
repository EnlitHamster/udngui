package it.taglio.listeners;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import it.taglio.gui.UDNGui;
import it.taglio.types.FuncInfo;

public class TreeAdapter implements TreeSelectionListener {

	private final UDNGui frame;

	public TreeAdapter(UDNGui frame) {
		this.frame = frame;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		if (frame.getNode() != null && frame.getNode().getUserObject() != null
				&& frame.getNode().getUserObject() instanceof FuncInfo)
			frame.undecorate(frame.getNode().toString());
	}

}
