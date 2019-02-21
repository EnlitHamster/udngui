package it.taglio;

import static it.taglio.Constants.dep_dir;
import static it.taglio.Constants.deps;
import static it.taglio.Constants.root;
import static it.taglio.Constants.sep;
import static it.taglio.Constants.v;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import it.taglio.gui.UDNGui;

public class Main {

	private static final String version = "0.3-alpha-01u";

	public static void main(String[] args) {
		if (!System.getProperty("os.name").contains("Windows")) {
			JOptionPane.showMessageDialog(null, "This utility can only run on Windows machines.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.err.println("This utility can only run on Windows machines.");
			System.exit(1);
		}

		if (!root.exists() || !valid())
			if (root.exists())
				clean(root.toPath());

		try {
			root.mkdirs();
			validate();
		} catch (Exception se) {
			JOptionPane.showMessageDialog(null,
					"Couldn't create necessary directories. Try to launch as administrator.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.err.println("Couldn't create necessary directories. Try to launch as administrator.");
			clean(root.toPath());
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

	private static boolean valid() {
		File versioning = new File(root.getPath() + sep + v);
		if (!versioning.exists())
			return false;

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(versioning));
			return reader.readLine().equals(version);
		} catch (Exception e) {
			return false;
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to properly read version!", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
	}

	private static void extract(String path, String file) throws Exception {
		InputStream stream = null;
		OutputStream output = null;

		try {
			stream = Main.class.getResourceAsStream(dep_dir + file);
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

	private static void clean(Path path) {
		try {
			if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
				DirectoryStream<Path> entries = Files.newDirectoryStream(path);
				for (Path entry : entries) {
					clean(entry);
				}
			}

			Files.delete(path);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to clear program directory", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	private static void validate() {
		FileWriter writer = null;
		
		try {
			File file = new File(root.getPath() + sep + v);
			writer = new FileWriter(file);
			writer.write(version);
			writer.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to validate installation", "Error",
					JOptionPane.ERROR_MESSAGE);
			clean(root.toPath());
			System.exit(1);
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to validate installation", "Error",
						JOptionPane.ERROR_MESSAGE);
				clean(root.toPath());
				System.exit(1);
			}
		}
	}

}
