package it.taglio.gui;

import static it.taglio.Constants.icon;
import static it.taglio.Constants.root;
import static it.taglio.Constants.sep;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import it.taglio.listeners.AppListner;
import it.taglio.listeners.FileChooserListener;
import it.taglio.listeners.TreeListener;
import it.taglio.types.FuncEntry;
import it.taglio.types.FuncInfo;

@SuppressWarnings("serial")
public class UDNGui extends JFrame {

	private JTextField textField;
	private JLabel lblFunctionNameTo;
	private JLabel lblUndecoratedFunctionName;
	private JTextPane textPane;
	private JButton btnUndecorate;
	private JSplitPane splitPane;
	private JFileChooser fileChooser;
	private JScrollPane scrollPane;
	private JTree tree;

	public UDNGui() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
		}

		// -----------
		// Frame setup
		// -----------

		// TODO: CREDIT THE GRAPHICS AUTHOR IN THE ABOUT TAB!
		// - Icons: App icon made by Situ Herrera @
		// https://www.flaticon.com/authors/situ-herrera

		setTitle("UnDName GUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon)));

		// ------------
		// Layout setup
		// ------------

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 235, 541, 0 };
		gridBagLayout.rowHeights = new int[] { 443, 20, 21, 23, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };

		// ---------------------
		// Content instantiation
		// ---------------------

		splitPane = new JSplitPane();
		fileChooser = new JFileChooser();
		scrollPane = new JScrollPane();
		tree = new JTree();
		lblFunctionNameTo = new JLabel("Function name to undecorate");
		textField = new JTextField();
		lblUndecoratedFunctionName = new JLabel("Undecorated function name");
		textPane = new JTextPane();
		btnUndecorate = new JButton("Undecorate");

		// ----------------
		// Containers setup
		// ----------------

		splitPane.setLeftComponent(fileChooser);
		scrollPane.setViewportView(tree);
		splitPane.setRightComponent(scrollPane);

		// ------------------
		// File chooser setup
		// ------------------

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Dinamic-link libraries", "dll"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setApproveButtonText("X");
		disableButtons(fileChooser);
		fileChooser.setApproveButtonText(null);

		// ----------
		// Tree setup
		// ----------

		((DefaultTreeModel) tree.getModel()).setRoot(null);
		((DefaultTreeModel) tree.getModel()).nodeChanged(null);
		tree.setCellRenderer(new DepTreeRenderer());

		// -------------------
		// Text initialization
		// -------------------

		textField.setColumns(10);
		textPane.setEditable(false);

		String clipboard = "";

		try {
			clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			File f = new File(Paths.get(clipboard).toString());
			if (f.exists())
				setDLL(f);
			else
				throw new Exception();
		} catch (Exception e) {
			textField.setText(clipboard);
		}

		// -----------------
		// Constraints setup
		// -----------------

		// Split pane
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.insets = new Insets(5, 5, 5, 5);
		gbc_splitPane.gridwidth = 2;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;

		// Function name label
		GridBagConstraints gbc_lblFunctionNameTo = new GridBagConstraints();
		gbc_lblFunctionNameTo.fill = GridBagConstraints.BOTH;
		gbc_lblFunctionNameTo.insets = new Insets(0, 5, 5, 5);
		gbc_lblFunctionNameTo.gridx = 0;
		gbc_lblFunctionNameTo.gridy = 1;

		// Text field
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;

		// Result label
		GridBagConstraints gbc_lblUndecoratedFunctionName = new GridBagConstraints();
		gbc_lblUndecoratedFunctionName.fill = GridBagConstraints.BOTH;
		gbc_lblUndecoratedFunctionName.insets = new Insets(0, 5, 5, 5);
		gbc_lblUndecoratedFunctionName.gridx = 0;
		gbc_lblUndecoratedFunctionName.gridy = 2;

		// Result text
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.anchor = GridBagConstraints.SOUTH;
		gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 2;

		// Button
		GridBagConstraints gbc_btnUndecorate = new GridBagConstraints();
		gbc_btnUndecorate.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnUndecorate.insets = new Insets(0, 0, 5, 5);
		gbc_btnUndecorate.gridx = 1;
		gbc_btnUndecorate.gridy = 3;

		// --------------------
		// Listeners & Handlers
		// --------------------

		AppListner drop = new AppListner(this);
		Toolkit.getDefaultToolkit().addAWTEventListener(drop, AWTEvent.KEY_EVENT_MASK);

		fileChooser.addPropertyChangeListener(new FileChooserListener(this));
		tree.addTreeSelectionListener(new TreeListener(this));

		// -------------------------
		// Building frame content...
		// -------------------------

		getContentPane().setLayout(gridBagLayout);
		getContentPane().add(splitPane, gbc_splitPane);
		getContentPane().add(lblFunctionNameTo, gbc_lblFunctionNameTo);
		getContentPane().add(textField, gbc_textField);
		getContentPane().add(lblUndecoratedFunctionName, gbc_lblUndecoratedFunctionName);
		getContentPane().add(textPane, gbc_textPane);
		getContentPane().add(btnUndecorate, gbc_btnUndecorate);

		// ---------------------
		// Drag and Drop handler
		// ---------------------

		setDrop(this, drop);

		// Initiate
		setVisible(true);
		btnUndecorate.requestFocus();
	}

	public void setDLL(File f) {
		fileChooser.setSelectedFile(f);
	}

	public DefaultMutableTreeNode getNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

	public void updateTree(String file) {
		FuncInfo[] functions = listDLL(file);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode((new File(file).getName()));
		createTree(top, functions);
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.setRoot(top);
		model.nodeChanged(top);
		tree.setSelectionPath(null);
	}

	public void undecorate(String value) {
		textField.setText(value);
		undecorate();
	}

	public void undecorate() {
		try {
			Runtime runtime = Runtime.getRuntime();
			String[] command = { root.getPath() + sep + "undname.exe", "?" + textField.getText() };
			Process process = runtime.exec(command);

			BufferedReader stdin = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String result = "", s;
			while ((s = stdin.readLine()) != null)
				result = result.concat(s);

			textPane.setText(result.substring(result.indexOf("is :- ") + 6).trim().replaceAll("\"", ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private FuncInfo[] listDLL(String dll) {
		String header = "ordinal";
		String footer = "Summary";
		try {
			Runtime runtime = Runtime.getRuntime();
			String[] command = { root.getPath() + sep + "dumpbin.exe", "/exports", dll };
			Process process = runtime.exec(command);

			BufferedReader stdin = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String result = "", s;
			while ((s = stdin.readLine()) != null)
				result = result.concat(s) + "\n";

			if (result.contains("fatal error"))
				throw new Exception();

			result = result.substring(result.lastIndexOf(header), result.indexOf(footer)).trim();
			result = result.replaceAll(" +", " ");

			String[] lines = result.split("\n");
			FuncInfo[] functions = new FuncInfo[lines.length - 2];

			int nEntries = getEntriesNum(lines[0]);

			for (int i = 0; i < lines.length - 2; ++i)
				functions[i] = getFunction(lines[i + 2], nEntries);

			return functions;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to open library.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		return new FuncInfo[0];
	}

	private static int getEntriesNum(String header) {
		String columns = header.trim();
		int count = 0;
		do
			++count;
		while ((columns = columns.substring(columns.indexOf(" ") + 1)).indexOf(" ") != -1);
		return count;
	}

	private static FuncInfo getFunction(String line, int n) {
		String f = line.trim();
		String[] info = f.split(" ");

		if (f.contains("[NONAME]"))
			return new FuncInfo(Integer.parseInt(info[0]), -1, "Unknown", Integer.parseInt(info[1], 16));
		else if (info.length > 3) {
			String name = info[3];
			for (int i = 4; i < info.length; ++i) {
				name = name.concat(" " + info[i]);
			}
			return new FuncInfo(Short.parseShort(info[0]), Short.parseShort(info[1], 16), name,
					Integer.parseInt(info[2], 16));
		} else
			return new FuncInfo(0, 0, f, 0);

	}

	private static void disableButtons(Container c) {
		for (Component comp : c.getComponents()) {
			if (comp instanceof JButton && ((((JButton) comp).getIcon() != null
					&& ((JButton) comp).getIcon().equals(UIManager.getIcon("FileChooser.newFolderIcon")))
					|| (((JButton) comp).getText() != null && ((JButton) comp).getText()
							.equalsIgnoreCase(UIManager.getString("FileChooser.cancelButtonText")))
					|| (((JButton) comp).getText() != null && ((JButton) comp).getText().equalsIgnoreCase("X"))))
				comp.setEnabled(false);
			else if (comp instanceof Container)
				disableButtons((Container) comp);
		}

		c.repaint();
	}

	private static void createTree(DefaultMutableTreeNode top, FuncInfo[] content) {
		for (FuncInfo fInfo : content) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fInfo);
			node.add(new DefaultMutableTreeNode(new FuncEntry("Ordinal", fInfo.ordinal)));
			node.add(new DefaultMutableTreeNode(new FuncEntry("Hint", fInfo.hint, (byte) 4)));
			node.add(new DefaultMutableTreeNode(new FuncEntry("Entry point", fInfo.entry_point, (byte) 8)));
			top.add(node);
		}
	}

	private static void setDrop(Component comp, AppListner listener) {
		new DropTarget(comp, DnDConstants.ACTION_COPY, listener, true);
		if (comp instanceof Container)
			for (Component c : ((Container) comp).getComponents())
				setDrop(c, listener);
	}
}
