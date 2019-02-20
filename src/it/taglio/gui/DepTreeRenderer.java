package it.taglio.gui;

import static it.taglio.Constants.entry;
import static it.taglio.Constants.info;
import static it.taglio.Constants.top;
import static it.taglio.Constants.unknown;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import it.taglio.types.FuncEntry;
import it.taglio.types.FuncInfo;

@SuppressWarnings("serial")
public class DepTreeRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

		if (tree.getModel().getRoot().equals(node)) {
			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(top));
			setIcon(new ImageIcon(icon.getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
		} else if (node.getUserObject() instanceof FuncInfo) {
			FuncInfo fInfo = (FuncInfo) node.getUserObject();
			Image icon = Toolkit.getDefaultToolkit()
					.getImage(getClass().getResource(fInfo.name.equalsIgnoreCase("Unknown") ? unknown : info));
			setIcon(new ImageIcon(icon.getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
		} else if (node.getUserObject() instanceof FuncEntry) {
			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(entry));
			setIcon(new ImageIcon(icon.getScaledInstance(16, 15, Image.SCALE_DEFAULT)));
		}

		return this;
	}

}
