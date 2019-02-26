package it.taglio.gui.options;

import static it.taglio.Constants.config;
import static it.taglio.Constants.max_recent_size;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class OptionSet {

	public static final String RUS = "recently_used_size", CCS = "check_clipboard_on_startup",
			AFI = "show_additional_function_info";

	public static final String[] opt_keys = { RUS, CCS, AFI };

	private Map<String, String> opt_map;
	private boolean saved = false;

	private OptionSet() {
		opt_map = new HashMap<String, String>();
	}

	private OptionSet init() {
		opt_map.put(RUS, "4");
		opt_map.put(CCS, "false");
		opt_map.put(AFI, "false");

		saved = write();

		return this;
	}

	private static String encode(String key, String value) {
		return key + ": " + value + System.getProperty("line.separator");
	}

	private static Entry<String, String> decode(String line) {
		String data = line.trim().replaceAll(" +", " ");
		String[] info = data.split(": ");

		if (info.length != 2)
			return null;
		else
			return new AbstractMap.SimpleEntry<String, String>(info[0], info[1]);
	}

	private static boolean isKey(String key) {
		for (String s : opt_keys)
			if (s.equalsIgnoreCase(key))
				return true;
		return false;
	}

	public boolean write() {
		FileWriter writer = null;

		try {
			Runtime.getRuntime().exec("attrib -H " + config).waitFor();

			writer = new FileWriter(new File(config));
			for (String key : opt_map.keySet()) {
				writer.write(encode(key, opt_map.get(key)));
			}

			Runtime.getRuntime().exec("attrib -H " + config);

			JOptionPane.showMessageDialog(null, "Options saved.", "Done", JOptionPane.INFORMATION_MESSAGE);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "An error occured while writing the options on file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

	}

	public static OptionSet read() {
		if (!(new File(config).exists()))
			return (new OptionSet()).init();

		BufferedReader reader = null;
		OptionSet set = null;

		try {
			Runtime.getRuntime().exec("attrib -H " + config).waitFor();

			reader = new BufferedReader(new FileReader(new File(config)));
			String line = null;
			set = new OptionSet();

			while ((line = reader.readLine()) != null) {
				Entry<String, String> entry = decode(line);

				if (!set.set(entry.getKey(), entry.getValue()))
					throw new UnrecognisedKeyException(entry.getKey());
			}

			Runtime.getRuntime().exec("attrib -H " + config);

			for (String key : opt_keys)
				if (!set.opt_map.keySet().contains(key))
					throw new IncompleteConfigException(key);

			set.saved = true;
		} catch (UnrecognisedKeyException uke) {
			JOptionPane.showMessageDialog(null, "Unrecognised config key: " + uke.getMessage() + ".", "Error",
					JOptionPane.ERROR_MESSAGE);
			set = null;
		} catch (IncompleteConfigException ice) {
			JOptionPane.showMessageDialog(null, "Incomplete config file. Missing: " + ice.getMessage() + ".", "Error",
					JOptionPane.ERROR_MESSAGE);
			set = null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occured while reading the options on file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			set = null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "An error occured while reading the options on file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				set = null;
			}
		}

		return set;
	}

	public boolean set(String key, String value) {
		try {
			if (isKey(key)) {
				if (key.equalsIgnoreCase(RUS)) {
					int i_val = Integer.parseInt(value);

					if (i_val >= 0 && i_val <= max_recent_size)
						opt_map.put(key, "" + value);
					else {
						JOptionPane.showMessageDialog(null,
								"File cache size must be a number between 0 and " + max_recent_size, "Attention",
								JOptionPane.WARNING_MESSAGE);
					}
				} else if (key.equalsIgnoreCase(AFI) || key.equalsIgnoreCase(CCS)) {
					Boolean.parseBoolean(value);
					opt_map.put(key, value);
				}

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public String get(String key) {
		if (isKey(key))
			return opt_map.get(key);

		return null;
	}

	public boolean isSaved() {
		return saved;
	}

}
