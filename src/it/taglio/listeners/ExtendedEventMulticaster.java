package it.taglio.listeners;

import java.awt.AWTEventMulticaster;
import java.util.EventListener;

public class ExtendedEventMulticaster extends AWTEventMulticaster implements OptionsUpdateListener {

	protected ExtendedEventMulticaster(EventListener a, EventListener b) {
		super(a, b);
	}

	protected static EventListener addInternal(EventListener a, EventListener b) {
		if (a == null)
			return b;
		if (b == null)
			return a;

		return new ExtendedEventMulticaster(a, b);
	}

	protected EventListener remove(EventListener old) {
		if (old == a)
			return b;
		if (old == b)
			return a;

		EventListener a2 = removeInternal(a, old);
		EventListener b2 = removeInternal(b, old);

		if (a2 == a && b2 == b)
			return this;

		return addInternal(a2, b2);
	}

	public static OptionsUpdateListener add(OptionsUpdateListener a, OptionsUpdateListener b) {
		return (OptionsUpdateListener) addInternal(a, b);
	}
	
	public static OptionsUpdateListener remove(OptionsUpdateListener l, OptionsUpdateListener old) {
		return (OptionsUpdateListener) removeInternal(l, old);
	}

	@Override
	public void updateOptions(OptionsUpdateEvent event) {
		if (a != null)
			((OptionsUpdateListener) a).updateOptions(event);
		if (b != null)
			((OptionsUpdateListener) b).updateOptions(event);
	}

}
