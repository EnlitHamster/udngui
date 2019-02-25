package it.taglio.types;

import static it.taglio.Constants.err_file_not_found;

import java.io.InputStream;

public class AboutEntry {

	private String name;
	private String page_path;

	public AboutEntry(String name, String page_path) {
		this.name = name;
		this.page_path = page_path;
	}

	public String toString() {
		return name;
	}

	public InputStream getAbout() {
		InputStream stream = getClass().getResourceAsStream(page_path);
		if (stream != null)
			return stream;
		else
			return getClass().getResourceAsStream(err_file_not_found);
	}

}
