
package org.eclipse.jgit.awtui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;

import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class AwtSshSessionFactory extends SshConfigSessionFactory {
	public static void install() {
		SshSessionFactory.setInstance(new AwtSshSessionFactory());
	}

	@Override
	protected void configure(final OpenSshConfig.Host hc
		if (!hc.isBatchMode())
			session.setUserInfo(new AWT_UserInfo());
	}

	private static class AWT_UserInfo implements UserInfo
			UIKeyboardInteractive {
		private String passwd;

		private String passphrase;

		public void showMessage(final String msg) {
			JOptionPane.showMessageDialog(null
		}

		public boolean promptYesNo(final String msg) {
			return JOptionPane.showConfirmDialog(null
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
		}

		public boolean promptPassword(final String msg) {
			passwd = null;
			final JPasswordField passwordField = new JPasswordField(20);
			final int result = JOptionPane.showConfirmDialog(null
					new Object[] { passwordField }
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				passwd = new String(passwordField.getPassword());
				return true;
			}
			return false;
		}

		public boolean promptPassphrase(final String msg) {
			passphrase = null;
			final JPasswordField passwordField = new JPasswordField(20);
			final int result = JOptionPane.showConfirmDialog(null
					new Object[] { passwordField }
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				passphrase = new String(passwordField.getPassword());
				return true;
			}
			return false;
		}

		public String getPassword() {
			return passwd;
		}

		public String getPassphrase() {
			return passphrase;
		}

		public String[] promptKeyboardInteractive(final String destination
				final String name
				final String[] prompt
			final GridBagConstraints gbc = new GridBagConstraints(0
					1
					GridBagConstraints.NONE
			final Container panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.weightx = 1.0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			panel.add(new JLabel(instruction)
			gbc.gridy++;

			gbc.gridwidth = GridBagConstraints.RELATIVE;

			final JTextField[] texts = new JTextField[prompt.length];
			for (int i = 0; i < prompt.length; i++) {
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridx = 0;
				gbc.weightx = 1;
				panel.add(new JLabel(prompt[i])

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weighty = 1;
				if (echo[i]) {
					texts[i] = new JTextField(20);
				} else {
					texts[i] = new JPasswordField(20);
				}
				panel.add(texts[i]
				gbc.gridy++;
			}

			if (JOptionPane.showConfirmDialog(null
					+ name
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
				String[] response = new String[prompt.length];
				for (int i = 0; i < prompt.length; i++) {
					response[i] = texts[i].getText();
				}
				return response;
			}
		}
	}
}
