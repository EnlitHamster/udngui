package it.taglio;

import java.io.File;

public class UDNConstants {

	public static final char sep = File.separatorChar;
	public static final File root = new File(System.getProperty("user.home") + sep + "udngui" + sep);
	public static final String[] deps = { "dumpbin.exe", "link.exe", "undname.exe", "mspdbcore.dll" };

}
