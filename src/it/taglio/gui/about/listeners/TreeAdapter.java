package it.taglio.gui.about.listeners;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import it.taglio.gui.about.AboutGui;
import it.taglio.types.AboutEntry;

public class TreeAdapter implements TreeSelectionListener {

	private final AboutGui dialog;

	public TreeAdapter(AboutGui dialog) {
		this.dialog = dialog;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		AboutEntry entry = (AboutEntry) ((DefaultMutableTreeNode) ((JTree) arg0.getSource()).getLastSelectedPathComponent()).getUserObject();
		dialog.setPage(entry.getAbout());
	}

}
