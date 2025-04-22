	}

	private void storeShellSize() {
		if (!shell.isDisposed()) {
			IDialogSettings dialogSettings = getDialogSettings();
			dialogSettings.put(DIALOG_HEIGHT, shell.getSize().y);
			dialogSettings.put(DIALOG_WIDTH, shell.getSize().x);
		}
