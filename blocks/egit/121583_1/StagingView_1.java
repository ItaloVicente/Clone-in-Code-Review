	private void gitmoji() {

		Collection<Gitmoji> gitmojis=GitmojiLibrary.gitmojis();

		if (gitmojis.isEmpty()) {
			MessageDialog.openError(getSite().getShell(), "Error", //$NON-NLS-1$
					"No gitmoji has been found. Please check your internet connection."); //$NON-NLS-1$
			return;
		}

		ElementListSelectionDialog dialog = new FilterGitmojiDialog(
				getSite().getShell(), gitmojis);

		if (dialog.open() != Window.OK) {
			return;
		}

		Object[] result = dialog.getResult();
		Gitmoji message = (Gitmoji) result[0];
		commitMessageText.setText(
				message.getCode() + ' ' + commitMessageText.getText());
	}

