package it.taglio.gui.about;

import static it.taglio.Constants.about;
import static it.taglio.Constants.about_me;
import static it.taglio.Constants.index;
import static it.taglio.Constants.stl_dir;
import static it.taglio.Constants.tutorial_0;
import static it.taglio.Constants.tutorial_1;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import it.taglio.gui.UDNDialog;
import it.taglio.gui.UDNGui;
import it.taglio.types.AboutCategory;
import it.taglio.types.AboutEntry;

public class AboutGui extends UDNDialog {

	private static final long serialVersionUID = -434316037816462936L;

	private JSplitPane splitPane;
	private JTree tree;
	private JLabel label;

	public AboutGui(UDNGui frame) {
		super(frame);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		JComponent.setDefaultLocale(Locale.ENGLISH);

		// -----------
		// Frame setup
		// -----------

		setTitle("About");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(about)));
		setResizable(true);
		setSize(450, 357);
		setModalityType(ModalityType.APPLICATION_MODAL);

		// ------------
		// Layout setup
		// ------------

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 434, 0 };
		gridBagLayout.rowHeights = new int[] { 318, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };

		// ---------------------
		// Content instantiation
		// ---------------------

		splitPane = new JSplitPane();
		tree = new JTree();
		label = new JLabel("");

		// ----------------
		// Containers setup
		// ----------------

		splitPane.setLeftComponent(tree);
		splitPane.setRightComponent(label);

		// ----------
		// Tree setup
		// ----------

		AboutCategory root = new AboutCategory("General Informations", index,
				new AboutEntry[] {
						new AboutCategory("Tutorials", tutorial_0,
								new AboutEntry[] { new AboutEntry("Clipboard features", tutorial_1) }),
						new AboutEntry("Developper", about_me) });
		DefaultMutableTreeNode rootNode = createTree(root);

		((DefaultTreeModel) tree.getModel()).setRoot(rootNode);
		((DefaultTreeModel) tree.getModel()).nodeChanged(rootNode);
		tree.setCellRenderer(new AboutTreeRenderer());

		// -----------------
		// Constraints setup
		// -----------------

		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;

		// --------------------
		// Listeners & Handlers
		// --------------------

		TreeAdapter lTree = new TreeAdapter(this);

		tree.addTreeSelectionListener(lTree);

		// -------------------------
		// Building frame content...
		// -------------------------

		getContentPane().setLayout(gridBagLayout);
		getContentPane().add(splitPane, gbc_splitPane);

		setVisible(true);
	}

	private DefaultMutableTreeNode createTree(AboutCategory top) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(top);

		for (AboutEntry entry : top.list()) {
			if (entry instanceof AboutCategory)
				root.add(createTree((AboutCategory) entry));
			else
				root.add(new DefaultMutableTreeNode(entry));
		}

		return root;
	}

	public void setPage(InputStream page) {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(page));
			String text = "", line = null;
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith("<!--importstyle:") && line.trim().endsWith("-->"))
					text = text.concat(importStyle(line.trim().replace("<!--importstyle:", "").replace("-->", "")));
				text = text.concat(line);
			}
			label.setText(text);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to load the information page", "Error",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

	private String importStyle(String stylesheet) {
		String style = "";
		BufferedReader reader = null;

		try {
			style = "<style type=\"text/css\">";
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(stl_dir + stylesheet)));
			String line = null;
			while ((line = reader.readLine()) != null)
				style = style.concat(line);
			style = style.concat("</style>");
		} catch (Exception e) {
			style = "";
			JOptionPane.showMessageDialog(null, "Unable to load the stylesheet", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		return style;
	}
}
