package it.taglio;

import java.io.File;

public class Constants {

	public static final char sep = File.separatorChar;
	public static final File root = new File(System.getProperty("user.home") + sep + "udngui" + sep);
	public static final String v = root.getAbsolutePath() + sep + ".v";
	public static final String cache = root.getAbsolutePath() + sep + ".recent";
	public static final String config = root.getAbsolutePath() + sep + "options.cfg";
	public static final String[] deps = { "dumpbin.exe", "link.exe", "undname.exe", "mspdbcore.dll" };

	public static final String dep_dir = "/res/dependencies/";
	public static final String ass_dir = "/res/assets/";
	public static final String img_dir = ass_dir + "images/";
	public static final String stl_dir = ass_dir + "style/";

	public static final String index = ass_dir + "index.html";
	public static final String about_me = ass_dir + "about.html";
	public static final String tutorial_0 = ass_dir + "tutorial/index.html";
	public static final String tutorial_1 = ass_dir + "tutorial/clipboard.html";
	public static final String err_file_not_found = ass_dir + "404.html";

	public static final String icon = img_dir + "icon.png";
	public static final String top = img_dir + "top.png";
	public static final String info = img_dir + "info.png";
	public static final String unknown = img_dir + "unknown.png";
	public static final String f_entry = img_dir + "function_entry.png";
	public static final String options = img_dir + "options.png";
	public static final String about = img_dir + "about.png";
	public static final String home = img_dir + "home.png";
	public static final String dir = img_dir + "dir.png";
	public static final String a_entry = img_dir + "about_entry.png";

	public static final int max_recent_size = 20;

}
