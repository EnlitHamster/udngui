package it.taglio.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.taglio.gui.UDNGui;
import it.taglio.gui.options.OptionSet;
import it.taglio.gui.options.OptionsGui;

public class OptionsAdapter implements ActionListener {

	private UDNGui frame;
	private OptionsGui dialog;
	private OptionSet set;

	public OptionsAdapter(UDNGui frame, OptionsGui dialog, OptionSet set) {
		this.frame = frame;
		this.dialog = dialog;
		this.set = set;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() instanceof JButton) {
			JButton btn = (JButton) action.getSource();

			boolean afi = dialog.getFunctioninfo().isSelected();
			boolean ccs = dialog.getCheckClipboardBox().isSelected();
			int rus = Integer.parseInt(dialog.getRecentFilesSize().getText());

			set.set(OptionSet.AFI, "" + afi);
			set.set(OptionSet.CCS, "" + ccs);
			set.set(OptionSet.RUS, "" + rus);

			if (dialog.getAccept().equals(btn))
				set.write();

			dialog.dispose();
			frame.fireOptionsUpdate(new OptionsUpdateEvent(dialog, OptionsUpdateEvent.OPTIONS_UPDATE, rus, afi, ccs));
		}
	}
}
