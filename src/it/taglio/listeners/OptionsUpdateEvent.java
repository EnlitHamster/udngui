package it.taglio.listeners;

import java.awt.AWTEvent;
import java.io.Serializable;

import it.taglio.gui.options.OptionsGui;

public class OptionsUpdateEvent extends AWTEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int OPTIONS_UPDATE = AWTEvent.RESERVED_ID_MAX + 1;
	
	protected int recently_used_size;
	protected boolean additional_function_informations;
	protected boolean check_clipboard_on_startup;

	public OptionsUpdateEvent(OptionsGui source, int id, int recently_used_size,
			boolean additional_function_informations, boolean check_clipboard_on_startup) {
		super(source, id);
		
		this.recently_used_size = recently_used_size;
		this.additional_function_informations = additional_function_informations;
		this.check_clipboard_on_startup = check_clipboard_on_startup;
	}
	
	public int getRecentlyUsedSize() {
		return recently_used_size;
	}
	
	public boolean getMoreFuncInfo() {
		return additional_function_informations;
	}
	
	public boolean getCheckClipboard() {
		return check_clipboard_on_startup;
	}

}
