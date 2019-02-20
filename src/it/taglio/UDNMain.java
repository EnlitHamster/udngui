package it.taglio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import it.taglio.gui.UDNGui;

import static it.taglio.UDNConstants.*;

public class UDNMain {

	public static void main(String[] args) {
		if (!System.getProperty("os.name").contains("Windows")) {
			JOptionPane.showMessageDialog(null, "This utility can only run on Windows machines.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.err.println("This utility can only run on Windows machines.");
			System.exit(1);
		}

		if (!((new File(root.getPath() + sep + "undname.exe")).exists()))
			try {
				if (!root.exists())
					root.mkdirs();
			} catch (SecurityException se) {
				JOptionPane.showMessageDialog(null,
						"Couldn't create necessary directories. Try to launch as administrator.", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.err.println("Couldn't create necessary directories. Try to launch as administrator.");
				se.printStackTrace();
				System.exit(1);
			}

		for (String dep : deps) {
			if (!((new File(root.getPath() + sep + dep)).exists())) {
				try {
					extract(root.getPath(), dep);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Couldn't extract " + dep + ".", "Error",
							JOptionPane.ERROR_MESSAGE);
					System.err.println("Couldn't extract " + dep + " executable.");
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		
		new UDNGui();
	}

	private static void extract(String path, String file) throws Exception {
		InputStream stream = null;
		OutputStream output = null;

		try {
			stream = UDNMain.class.getResourceAsStream("/res/" + file);
			if (stream == null)
				throw new Exception("Cannot get resources from jar file");

			int bytes;
			byte[] buffer = new byte[4096];
			String file_path = path + sep + file;
			output = new FileOutputStream(file_path);

			while ((bytes = stream.read(buffer)) > 0)
				output.write(buffer, 0, bytes);
		} catch (Exception e) {
			throw e;
		} finally {
			stream.close();
			output.close();
		}
	}

}
