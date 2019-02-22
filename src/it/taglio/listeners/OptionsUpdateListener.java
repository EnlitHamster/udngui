package it.taglio.listeners;

import java.util.EventListener;

public interface OptionsUpdateListener extends EventListener {
	
	void updateOptions(OptionsUpdateEvent arg0);
	
}
