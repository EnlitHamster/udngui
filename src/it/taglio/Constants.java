package it.taglio;

import java.io.File;

public class Constants {

	public static final String version = "0.3-alpha-01u";

	public static final char sep = File.separatorChar;
	public static final String root_path = System.getProperty("user.home") + sep + "udngui" + sep;
	public static final String v = root_path + ".v";
	public static final String cache = root_path + ".recent";
	public static final String config = root_path + "options.cfg";
	public static final String[] deps = { "dumpbin.exe", "link.exe", "undname.exe", "mspdbcore.dll" };

	public static final String dep_dir = "/res/dependencies/";
	public static final String ass_dir = "/res/assets";
	public static final String doc_dir = root_path + "docs" + sep;
	
	public static final String root_online_dir = "https://raw.githubusercontent.com/EnlitHamster/udngui/master/docs/0.3-alpha-01u/";
	public static final String[] doc_files = {
			"index.html",
			"about.html",
			"tutorials/index.html",
			"tutorials/clipboard.html",
			"errors/404.html"
	};

	public static final int index = 0;
	public static final int about_me = 1;
	public static final int tutorial_0 = 2;
	public static final int tutorial_1 = 3;
	public static final File err_file_not_found = new File(doc_dir + doc_files[4]);

	public static final String icon = ass_dir + "icon.png";
	public static final String top = ass_dir + "top.png";
	public static final String info = ass_dir + "info.png";
	public static final String unknown = ass_dir + "unknown.png";
	public static final String f_entry = ass_dir + "function_entry.png";
	public static final String options = ass_dir + "options.png";
	public static final String about = ass_dir + "about.png";
	public static final String home = ass_dir + "home.png";
	public static final String dir = ass_dir + "dir.png";
	public static final String a_entry = ass_dir + "about_entry.png";

	public static final File root = new File(root_path);
	public static final int max_recent_size = 20;

}
