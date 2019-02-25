package it.taglio.types;

import java.util.ArrayList;
import java.util.List;

public class AboutCategory extends AboutEntry {

	private List<AboutEntry> entries;

	public AboutCategory(String name, String file_path, AboutEntry[] entries) {
		super(name, file_path);

		List<AboutEntry> tmp = new ArrayList<AboutEntry>();
		for (int i = 0; i < entries.length; ++i)
			tmp.add(entries[i]);
		this.entries = tmp;
	}

	public AboutCategory(String name, String file_path, List<AboutEntry> entries) {
		super(name, file_path);

		this.entries = entries;
	}

	public List<AboutEntry> list() {
		return entries;
	}

}
