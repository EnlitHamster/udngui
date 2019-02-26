package it.taglio.gui.options.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import it.taglio.Main;
import it.taglio.gui.UDNGui;
import it.taglio.gui.options.OptionSet;
import it.taglio.gui.options.OptionsGui;
import it.taglio.listeners.OptionsUpdateEvent;

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
		try {
			if (action.getSource() instanceof JButton) {
				JButton btn = (JButton) action.getSource();

				boolean afi = dialog.getFunctioninfo().isSelected();
				boolean ccs = dialog.getCheckClipboardBox().isSelected();
				int rus = Integer.parseInt(dialog.getRecentFilesSize().getText());

				if (dialog.getAcceptClose().equals(btn) || dialog.getAccept().equals(btn)) {
					if (set.set(OptionSet.AFI, "" + afi) && set.set(OptionSet.CCS, "" + ccs)
							&& set.set(OptionSet.RUS, "" + rus)) {

						set.write();
						frame.fireOptionsUpdate(
								new OptionsUpdateEvent(dialog, OptionsUpdateEvent.OPTIONS_UPDATE, rus, afi, ccs));

						if (dialog.getAcceptClose().equals(btn))
							dialog.dispose();
					}
				} else if (dialog.getCancel().equals(btn))
					dialog.dispose();
				else if (dialog.getClearCache().equals(btn))
					Main.resetRecent();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error in the options.", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}
