package it.taglio.gui.about;

import static it.taglio.Constants.a_entry;
import static it.taglio.Constants.dir;
import static it.taglio.Constants.home;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import it.taglio.types.AboutCategory;
import it.taglio.types.AboutEntry;

public class AboutTreeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 3256107678041726847L;
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		if (tree.getModel().getRoot().equals(node)) {
			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(home));
			setIcon(new ImageIcon(icon.getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
		} else if (node.getUserObject() instanceof AboutCategory) {
			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(dir));
			setIcon(new ImageIcon(icon.getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
		} else if (node.getUserObject() instanceof AboutEntry) {
			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(a_entry));
			setIcon(new ImageIcon(icon.getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
		}

		return this;
	}

}
