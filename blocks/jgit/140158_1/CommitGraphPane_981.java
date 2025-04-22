
package org.eclipse.jgit.awtui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.ChainingCredentialsProvider;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.NetRCCredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class AwtCredentialsProvider extends CredentialsProvider {
	public static void install() {
		final AwtCredentialsProvider c = new AwtCredentialsProvider();
		CredentialsProvider cp = new ChainingCredentialsProvider(
				new NetRCCredentialsProvider()
		CredentialsProvider.setDefault(cp);
	}

	@Override
	public boolean isInteractive() {
		return true;
	}

	@Override
	public boolean supports(CredentialItem... items) {
		for (CredentialItem i : items) {
			if (i instanceof CredentialItem.StringType)
				continue;

			else if (i instanceof CredentialItem.CharArrayType)
				continue;

			else if (i instanceof CredentialItem.YesNoType)
				continue;

			else if (i instanceof CredentialItem.InformationalMessage)
				continue;

			else
				return false;
		}
		return true;
	}

	@Override
	public boolean get(URIish uri
			throws UnsupportedCredentialItem {
		if (items.length == 0) {
			return true;

		} else if (items.length == 1) {
			final CredentialItem item = items[0];

			if (item instanceof CredentialItem.InformationalMessage) {
				JOptionPane.showMessageDialog(null
						UIText.get().warning
				return true;

			} else if (item instanceof CredentialItem.YesNoType) {
				CredentialItem.YesNoType v = (CredentialItem.YesNoType) item;
				int r = JOptionPane.showConfirmDialog(null
						UIText.get().warning
				switch (r) {
				case JOptionPane.YES_OPTION:
					v.setValue(true);
					return true;

				case JOptionPane.NO_OPTION:
					v.setValue(false);
					return true;

				case JOptionPane.CANCEL_OPTION:
				case JOptionPane.CLOSED_OPTION:
				default:
					return false;
				}

			} else {
				return interactive(uri
			}

		} else {
			return interactive(uri
		}
	}

	private static boolean interactive(URIish uri
		final GridBagConstraints gbc = new GridBagConstraints(0
				GridBagConstraints.NORTHWEST
				new Insets(0
		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		final JTextField[] texts = new JTextField[items.length];
		for (int i = 0; i < items.length; i++) {
			CredentialItem item = items[i];

			if (item instanceof CredentialItem.StringType
					|| item instanceof CredentialItem.CharArrayType) {
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridwidth = GridBagConstraints.RELATIVE;
				gbc.gridx = 0;
				panel.add(new JLabel(item.getPromptText())

				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.gridwidth = GridBagConstraints.RELATIVE;
				gbc.gridx = 1;
				if (item.isValueSecure())
					texts[i] = new JPasswordField(20);
				else
					texts[i] = new JTextField(20);
				panel.add(texts[i]
				gbc.gridy++;

			} else if (item instanceof CredentialItem.InformationalMessage) {
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.gridx = 0;
				panel.add(new JLabel(item.getPromptText())
				gbc.gridy++;

			} else {
				throw new UnsupportedCredentialItem(uri
			}
		}

		if (JOptionPane.showConfirmDialog(null
				UIText.get().authenticationRequired
				JOptionPane.OK_CANCEL_OPTION

		for (int i = 0; i < items.length; i++) {
			CredentialItem item = items[i];
			JTextField f = texts[i];

			if (item instanceof CredentialItem.StringType) {
				CredentialItem.StringType v = (CredentialItem.StringType) item;
				if (f instanceof JPasswordField)
					v.setValue(new String(((JPasswordField) f).getPassword()));
				else
					v.setValue(f.getText());

			} else if (item instanceof CredentialItem.CharArrayType) {
				CredentialItem.CharArrayType v = (CredentialItem.CharArrayType) item;
				if (f instanceof JPasswordField)
					v.setValueNoCopy(((JPasswordField) f).getPassword());
				else
					v.setValueNoCopy(f.getText().toCharArray());
			}
		}
		return true;
	}
}
