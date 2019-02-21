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
	
	public static final String[] opt_keys = {RUS, CCS, AFI};

	private Map<String, String> opt_map;

	private OptionSet init() {
		opt_map = new HashMap<String, String>();
		opt_map.put(RUS, "4");
		opt_map.put(CCS, "false");
		opt_map.put(AFI, "false");

		write();

		return this;
	}
	
	private static String encode(String key, String value) {
		return key + ": " + value;
	}
	
	private static Entry<String,String> decode(String line) {
		String data = line.trim().replaceAll(" +", " ");
		String[] info = data.split(": ");
		
		if (info.length != 2)
			return null;
		else
			return new AbstractMap.SimpleEntry<String, String> (info[0], info[1]);
	}
	
	private static boolean isKey(String key) {
		for(String s : opt_keys)
			if (s.equalsIgnoreCase(key))
				return true;
		return false;
	}

	public boolean write() {
		FileWriter writer = null;
		
		try {
			Runtime.getRuntime().exec("attrib -H " + config).waitFor();
			
			writer = new FileWriter(new File(config));
			for (String key : opt_map.keySet()) 
				writer.write(encode(key, opt_map.get(key)));
			
			Runtime.getRuntime().exec("attrib -H " + config);
				
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "An error occured while writing the options on file.", "Error", JOptionPane.ERROR_MESSAGE);
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
				if (isKey(entry.getKey()))
					set.opt_map.put(entry.getKey(), entry.getValue());
				else 
					throw new UnrecognisedKeyException(entry.getKey());
			}

			Runtime.getRuntime().exec("attrib -H " + config);		
			
			for (String key : opt_keys)
				if (!set.opt_map.keySet().contains(key))
					throw new IncompleteConfigException(key);
		} catch(UnrecognisedKeyException uke) {
			JOptionPane.showMessageDialog(null, "Unrecognised config key: " + uke.getMessage() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			set = null;
		} catch(IncompleteConfigException ice) {
			JOptionPane.showMessageDialog(null, "Incomplete config file. Missing: " + ice.getMessage() + ".", "Error", JOptionPane.ERROR_MESSAGE);
			set = null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error occured while reading the options on file.", "Error", JOptionPane.ERROR_MESSAGE);
			set = null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "An error occured while reading the options on file.", "Error", JOptionPane.ERROR_MESSAGE);
				set = null;
			}
		}
		
		return set;		
	}

	public boolean setCacheDim(int value) {
		if (value >= 0 && value <= max_recent_size) {
			opt_map.put(RUS, "" + value);
			return true;
		} else {
			JOptionPane.showConfirmDialog(null, "File cache size must be a number between 0 and " + max_recent_size
					+ ".\nDo you want to change it?", "Attention", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

}
