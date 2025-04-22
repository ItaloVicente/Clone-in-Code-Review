
package org.eclipse.jgit.awtui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.PasswordAuthentication;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.eclipse.jgit.util.CachedAuthenticator;

public class AwtAuthenticator extends CachedAuthenticator {
	public static void install() {
		setDefault(new AwtAuthenticator());
	}

	@Override
	protected PasswordAuthentication promptPasswordAuthentication() {
		final GridBagConstraints gbc = new GridBagConstraints(0
				GridBagConstraints.NORTHWEST
				new Insets(0
		final Container panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		final StringBuilder instruction = new StringBuilder();
		instruction.append(UIText.get().enterUsernameAndPasswordFor);
		if (getRequestorType() == RequestorType.PROXY) {
			instruction.append(getRequestorType());
			instruction.append(getRequestingHost());
			if (getRequestingPort() > 0) {
				instruction.append(getRequestingPort());
			}
		} else {
			instruction.append(getRequestingURL());
		}

		gbc.weightx = 1.0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		panel.add(new JLabel(instruction.toString())
		gbc.gridy++;

		gbc.gridwidth = GridBagConstraints.RELATIVE;

		final JTextField username;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.weightx = 1;
		panel.add(new JLabel(UIText.get().username)

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 1;
		username = new JTextField(20);
		panel.add(username
		gbc.gridy++;

		final JPasswordField password;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.weightx = 1;
		panel.add(new JLabel(UIText.get().password)

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 1;
		password = new JPasswordField(20);
		panel.add(password
		gbc.gridy++;

		if (JOptionPane.showConfirmDialog(null
				UIText.get().authenticationRequired
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
			return new PasswordAuthentication(username.getText()
					.getPassword());
		}

	}
}
