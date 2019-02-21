package it.taglio;

import java.io.File;

public class Constants {

	public static final char sep = File.separatorChar;
	public static final File root = new File(System.getProperty("user.home") + sep + "udngui" + sep);
	public static final String v = root.getAbsolutePath() + sep + ".v";
	public static final String cache = root.getAbsolutePath() + sep + ".recent";
	public static final String[] deps = { "dumpbin.exe", "link.exe", "undname.exe", "mspdbcore.dll" };
	
	public static final String dep_dir = "/res/dependencies/";
	public static final String ass_dir = "/res/assets/";
	public static final String icon = ass_dir + "icon.png";
	public static final String top = ass_dir + "top.png";
	public static final String info = ass_dir + "info.png";
	public static final String unknown = ass_dir + "unknown.png";
	public static final String entry = ass_dir + "entry.png";
	
	public static final int recent_size = 5;
	
}
