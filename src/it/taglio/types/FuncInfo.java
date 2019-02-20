package it.taglio.types;

public class FuncInfo {

	public final short ordinal;
	public final short hint;
	public final String name;
	public final int entry_point;

	public FuncInfo(int ordinal, int hint, String name, int entry) throws NumberFormatException {
		this((short) Short.parseShort("" + ordinal), (short) Short.parseShort("" + hint), name, entry);
	}

	public FuncInfo(short ordinal, short hint, String name, int entry) {
		this.ordinal = ordinal;
		this.hint = hint;
		this.name = name;
		this.entry_point = entry;
	}
	
	public String toString() {
		return name;
	}

}
