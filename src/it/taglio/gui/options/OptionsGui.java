package it.taglio.gui.options;

import static it.taglio.Constants.options;

import java.awt.Color;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import it.taglio.gui.UDNDialog;
import it.taglio.gui.UDNGui;
import it.taglio.listeners.OptionsAdapter;

public class OptionsGui extends UDNDialog {
	
	private static final long serialVersionUID = 1L;

	// Cache
	private JPanel panelCache;
	private JLabel lblNumberOfCached;
	private JFormattedTextField textField;
	private JButton btnClearCache;

	// General
	private JPanel panelGeneral;
	private JCheckBox chckbxCheckClipboard;

	// DLL
	private JPanel panelDLL;
	private JCheckBox chckbxAdditionalFunctionInfo;

	// Save/Discard
	private JButton btnCancel;
	private JButton btnAccept;
	
	private OptionSet opts;

	public OptionsGui(UDNGui frame) {
		super(frame);

		opts = OptionSet.read();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		JComponent.setDefaultLocale(Locale.ENGLISH);

		// -----------
		// Frame setup
		// -----------

		setTitle("Options");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(options)));
		setResizable(false);
		setSize(450, 226);
		setModalityType(ModalityType.APPLICATION_MODAL);

		// -------
		// Formats
		// -------

		NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
		formatter.setValueClass(Integer.class);
		formatter.setAllowsInvalid(false);

		// ---------------------
		// Content instantiation
		// ---------------------

		// Cache panel
		panelCache = new JPanel();
		lblNumberOfCached = new JLabel("Number of cached files");
		textField = new JFormattedTextField(formatter);
		btnClearCache = new JButton("Clear cache");

		// General panel
		panelGeneral = new JPanel();
		chckbxCheckClipboard = new JCheckBox("Use clipboard content");

		// DLL panel
		panelDLL = new JPanel();
		chckbxAdditionalFunctionInfo = new JCheckBox("Additional function info");
		
		// Controls
		btnAccept = new JButton("Apply & close");
		btnCancel = new JButton("Cancel");

		// -------------
		// Layouts setup
		// -------------

		// General
		getContentPane().setLayout(null);

		// Panels
		panelCache.setLayout(null);
		panelGeneral.setLayout(null);
		panelDLL.setLayout(null);

		// ----------------
		// Boundaries setup
		// ----------------

		// Main
		panelCache.setBounds(10, 10, 424, 81);
		panelGeneral.setBounds(10, 102, 207, 50);
		panelDLL.setBounds(227, 102, 207, 50);
		btnCancel.setBounds(334, 163, 100, 23);
		btnAccept.setBounds(224, 163, 100, 23);

		// Cache panel
		lblNumberOfCached.setBounds(10, 16, 109, 20);
		textField.setBounds(129, 16, 285, 20);
		btnClearCache.setBounds(325, 47, 89, 23);
		
		// General panel
		chckbxCheckClipboard.setBounds(10, 16, 187, 23);
		
		// DLL panel
		chckbxAdditionalFunctionInfo.setBounds(10, 16, 187, 23);

		// -------------
		// Borders setup
		// -------------

		panelCache.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "Cache",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));

		panelGeneral.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "General",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));

		panelDLL.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "DLL explorer",
				TitledBorder.TRAILING, TitledBorder.TOP, null, Color.GRAY));

		// --------------------
		// Listeners & Handlers
		// --------------------

		OptionsAdapter lOpt = new OptionsAdapter(frame, this, opts);

		btnAccept.addActionListener(lOpt);
		btnCancel.addActionListener(lOpt);

		// ------
		// Params
		// ------

		textField.setColumns(10);
		
		textField.setText(opts.get(OptionSet.RUS));
		chckbxAdditionalFunctionInfo.setSelected(Boolean.parseBoolean(opts.get(OptionSet.AFI)));
		chckbxCheckClipboard.setSelected(Boolean.parseBoolean(opts.get(OptionSet.CCS)));

		// -------------------------
		// Building frame content...
		// -------------------------

		panelCache.add(lblNumberOfCached);
		panelCache.add(textField);
		panelCache.add(btnClearCache);

		panelGeneral.add(chckbxCheckClipboard);

		panelDLL.add(chckbxAdditionalFunctionInfo);
		
		getContentPane().add(panelCache);
		getContentPane().add(panelGeneral);
		getContentPane().add(panelDLL);
		getContentPane().add(btnCancel);
		getContentPane().add(btnAccept);

		// Initiate
		setVisible(true);
	}

	public JButton getAccept() {
		return btnAccept;
	}
	
	public JCheckBox getCheckClipboardBox() {
		return chckbxCheckClipboard;
	}
	
	public JCheckBox getFunctioninfo() {
		return chckbxAdditionalFunctionInfo;
	}
	
	public JFormattedTextField getRecentFilesSize() {
		return textField;
	}
}
