package it.taglio.gui.options;

import static it.taglio.Constants.options;

import java.awt.Color;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class OptionsGui extends JDialog {
	
	private JPanel panel;
	private JLabel lblNumberOfCached;
	private JFormattedTextField textField;
	private JButton btnClearCache;
	private JCheckBox chckbxAdditionalFunctionInfo;
	private JButton btnNewButton;
	private JButton btnAccept;

	public OptionsGui() {
		
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
		panel = new JPanel();
		lblNumberOfCached = new JLabel("Number of cached files");
		textField = new JFormattedTextField(formatter);
		btnClearCache = new JButton("Clear cache");

		// -------------
		// Layouts setup
		// -------------

		// General
		getContentPane().setLayout(null);

		// Panels
		panel.setLayout(null);

		// ----------------
		// Boundaries setup
		// ----------------

		// Panels
		panel.setBounds(10, 10, 424, 81);

		// Cache panel
		lblNumberOfCached.setBounds(10, 16, 109, 20);
		textField.setBounds(129, 16, 285, 20);
		btnClearCache.setBounds(325, 47, 89, 23);

		// -------------
		// Borders setup
		// -------------

		panel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "Cache",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));

		// ------
		// Params
		// ------
		
		textField.setColumns(10);

		// -------------------------
		// Building frame content...
		// -------------------------

		panel.add(lblNumberOfCached);
		panel.add(textField);
		panel.add(btnClearCache);

		getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "General", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panel_1.setBounds(10, 102, 207, 50);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Use clipboard content");
		chckbxNewCheckBox.setBounds(10, 16, 187, 23);
		panel_1.add(chckbxNewCheckBox);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "DLL explorer", TitledBorder.TRAILING, TitledBorder.TOP, null, Color.GRAY));
		panel_2.setBounds(227, 102, 207, 50);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		chckbxAdditionalFunctionInfo = new JCheckBox("Additional function info");
		chckbxAdditionalFunctionInfo.setBounds(10, 16, 187, 23);
		panel_2.add(chckbxAdditionalFunctionInfo);
		
		btnNewButton = new JButton("Cancel");
		btnNewButton.setBounds(334, 163, 100, 23);
		getContentPane().add(btnNewButton);
		
		btnAccept = new JButton("Apply & close");
		btnAccept.setBounds(224, 163, 100, 23);
		getContentPane().add(btnAccept);

		// Initiate
		setVisible(true);
	}
}
