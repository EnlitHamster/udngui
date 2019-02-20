package it.taglio.types;

public class FuncEntry {

	public final String key;
	public final int value;
	private final byte digits;

	public FuncEntry(String key, int value) {
		this.key = key;
		this.value = value;
		this.digits = 0;
	}

	public FuncEntry(String key, int value, byte digits) {
		this.key = key;
		this.value = value;
		this.digits = digits;
	}

	public String toString() {
		String value = "";
		if (this.value == -1)
			value = "?";
		else if (digits != 0) {
			value = "0x";
			String tmp = Integer.toHexString(this.value);
			for (byte b = 0; b < digits - tmp.length(); ++b)
				value = value.concat("0");
			value = value.concat(tmp.toUpperCase());
		} else
			value = Integer.toString(this.value);

		return key + " = " + value;
	}

}
