package it.taglio;

import static it.taglio.Constants.cache;
import static it.taglio.Constants.dep_dir;
import static it.taglio.Constants.deps;
import static it.taglio.Constants.doc_dir;
import static it.taglio.Constants.doc_dirs;
import static it.taglio.Constants.doc_files;
import static it.taglio.Constants.max_recent_size;
import static it.taglio.Constants.root;
import static it.taglio.Constants.root_online_dir;
import static it.taglio.Constants.root_path;
import static it.taglio.Constants.v;
import static it.taglio.Constants.version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import it.taglio.gui.UDNGui;
import it.taglio.gui.options.OptionSet;

public class Main {

	private static int recently_used_size = max_recent_size;
	private static LinkedList<File> recent;

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
			if (!((new File(root_path + dep)).exists())) {
				try {
					extract(root_path, dep);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Couldn't extract " + dep + ".", "Error",
							JOptionPane.ERROR_MESSAGE);
					System.err.println("Couldn't extract " + dep + " executable.");
					e.printStackTrace();
					System.exit(1);
				}
			}
		}

		downloadDocs();

		boolean add_finfo, check_clip;

		{
			OptionSet set = OptionSet.read();
			recently_used_size = Math.max(0, Math.min(max_recent_size, Integer.parseInt(set.get(OptionSet.RUS))));
			add_finfo = Boolean.parseBoolean(set.get(OptionSet.AFI));
			check_clip = Boolean.parseBoolean(set.get(OptionSet.CCS));
		}

		recent = loadRecent();
		new UDNGui(add_finfo, check_clip);
	}

	public static Iterator<File> getRecent() {
		if (recent == null)
			return null;
		return recent.descendingIterator();
	}

	public static void updateRecent(File file) {
		if (recent == null)
			recent = new LinkedList<File>();

		if (file.exists()) {
			if (recent.contains(file))
				recent.remove(file);

			recent.add(file);
			if (recent.size() > recently_used_size)
				recent.poll();
		}
	}

	public static void saveRecent() {
		if (recent == null)
			return;

		FileWriter writer = null;

		try {
			Runtime.getRuntime().exec("attrib -H " + cache).waitFor();

			writer = new FileWriter(new File(cache));
			File f = null;

			while ((f = recent.poll()) != null)
				writer.write(f.getAbsolutePath() + System.getProperty("line.separator"));

			Runtime.getRuntime().exec("attrib +H " + cache);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to save recently opened DLLs", "Error",
					JOptionPane.ERROR_MESSAGE);
			clean((new File(cache)).toPath());
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	private static boolean valid() {
		if (!(new File(v)).exists())
			return false;

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(v));
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

	private static void validate() {
		FileWriter writer = null;

		try {
			Runtime.getRuntime().exec("attrib -H " + v).waitFor();

			writer = new FileWriter(new File(v));
			writer.write(version);
			writer.close();

			Runtime.getRuntime().exec("attrib +H " + v);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to validate installation", "Error", JOptionPane.ERROR_MESSAGE);
			clean(root.toPath());
			System.exit(1);
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unable to validate installation", "Error",
						JOptionPane.ERROR_MESSAGE);
				clean(root.toPath());
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
			String file_path = path + file;
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

	private static LinkedList<File> loadRecent() {
		if (!(new File(cache)).exists())
			return null;

		LinkedList<File> list = new LinkedList<File>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(cache));
			String line = null;

			while ((line = reader.readLine()) != null) {
				File file = new File(line);
				if (file.exists() && list.size() < recently_used_size)
					list.add(file);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to save recently opened DLLs", "Error",
					JOptionPane.ERROR_MESSAGE);
			clean((new File(cache)).toPath());
			list = null;
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
			}
		}

		return list;
	}

	private static void downloadDocs() {
		for (String file : doc_dirs)
			if (!(new File(file)).exists())
				(new File(file)).mkdirs();

		FileOutputStream stream = null;
		FileWriter writer = null;

		try {
			for (String file : doc_files) {
				File tmp = null;
				if (!(tmp = new File(doc_dir + file)).exists()) {
					writer = new FileWriter(tmp);
					writer.close();

					URL ws = new URL(root_online_dir + file);
					ReadableByteChannel rbc = Channels.newChannel(ws.openStream());
					stream = new FileOutputStream(tmp);
					stream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setRecentlyUsedSize(int rus) {
		if (rus >= 0 && rus <= max_recent_size)
			recently_used_size = rus;

		resizeRecent();
	}

	private static void resizeRecent() {
		if (recent != null) {
			LinkedList<File> newList = new LinkedList<File>();
			for (int i = 0; i < recently_used_size && i < recent.size(); ++i)
				if (recent.get(i) != null)
					newList.add(recent.get(i));
		}
	}

	public static void resetRecent() {
		if (!(new File(cache)).exists())
			return;

		try {
			Runtime.getRuntime().exec("attrib -H " + cache).waitFor();
			Files.delete((new File(cache)).toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		recent = loadRecent();
	}

}
