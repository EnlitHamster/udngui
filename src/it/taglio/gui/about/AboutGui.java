package it.taglio.gui.about;

import static it.taglio.Constants.about;
import static it.taglio.Constants.about_me;
import static it.taglio.Constants.index;
import static it.taglio.Constants.tutorial_0;
import static it.taglio.Constants.tutorial_1;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import it.taglio.gui.UDNDialog;
import it.taglio.gui.UDNGui;
import it.taglio.gui.about.listeners.TreeAdapter;
import it.taglio.types.AboutCategory;
import it.taglio.types.AboutEntry;

public class AboutGui extends UDNDialog {

	private static final long serialVersionUID = -434316037816462936L;

	private JSplitPane splitPane;
	private JTree tree;
	private JEditorPane webpage;
	private JScrollPane panel;

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
		setSize(620, 475);
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
		panel = new JScrollPane();
		webpage = new JEditorPane();

		// -----------------
		// Constraints setup
		// -----------------

		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		
		// ---------------
		// Containrs setup
		// ---------------

		panel.setViewportView(webpage);
		splitPane.setLeftComponent(tree);
		splitPane.setRightComponent(panel);

		// -------------
		// Webpage setup
		// -------------

		webpage.setEditable(false);

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

		// --------------------
		// Listeners & Handlers
		// --------------------

		TreeAdapter lTree = new TreeAdapter(this);

		tree.addTreeSelectionListener(lTree);

		// --------------------
		// Listeners & Handlers
		// --------------------

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

	public void setPage(File page) {
		try {
			webpage.setPage(page.toURI().toURL());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to load the information page", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
