		if (!hc.isBatchMode())
			session.setUserInfo(new AWT_UserInfo());
	}

	private static class AWT_UserInfo implements UserInfo,
			UIKeyboardInteractive {
		private String passwd;

		private String passphrase;

		public void showMessage(final String msg) {
			JOptionPane.showMessageDialog(null, msg);
		}

		public boolean promptYesNo(final String msg) {
			return JOptionPane.showConfirmDialog(null, msg, "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
		}

		public boolean promptPassword(final String msg) {
			passwd = null;
			final JPasswordField passwordField = new JPasswordField(20);
			final int result = JOptionPane.showConfirmDialog(null,
					new Object[] { passwordField }, msg,
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
			final int result = JOptionPane.showConfirmDialog(null,
					new Object[] { passwordField }, msg,
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

		public String[] promptKeyboardInteractive(final String destination,
				final String name, final String instruction,
				final String[] prompt, final boolean[] echo) {
			final GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1,
					1, 1, GridBagConstraints.NORTHWEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
			final Container panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.weightx = 1.0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			panel.add(new JLabel(instruction), gbc);
			gbc.gridy++;

			gbc.gridwidth = GridBagConstraints.RELATIVE;

			final JTextField[] texts = new JTextField[prompt.length];
			for (int i = 0; i < prompt.length; i++) {
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridx = 0;
				gbc.weightx = 1;
				panel.add(new JLabel(prompt[i]), gbc);

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weighty = 1;
				if (echo[i]) {
					texts[i] = new JTextField(20);
				} else {
					texts[i] = new JPasswordField(20);
				}
				panel.add(texts[i], gbc);
				gbc.gridy++;
			}

			if (JOptionPane.showConfirmDialog(null, panel, destination + ": "
					+ name, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
				String[] response = new String[prompt.length];
				for (int i = 0; i < prompt.length; i++) {
					response[i] = texts[i].getText();
				}
				return response;
			}
		}
