package it.taglio.types;

import static it.taglio.Constants.doc_dir;
import static it.taglio.Constants.err_file_not_found;

import java.io.File;

public class AboutEntry {

	private String name;
	private File page;

	public AboutEntry(String name, String page_path) {
		this.name = name;
		this.page = new File(doc_dir + page_path);
	}

	public String toString() {
		return name;
	}

	public File getAbout() {
		if (page.exists())
			return page;
		else
			return err_file_not_found;
	}

}
