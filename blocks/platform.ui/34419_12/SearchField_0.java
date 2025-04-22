	}

	private void storeShellSize() {
		if (!shell.isDisposed()) {
			Point size = shell.getSize();
			dialogHeight = size.y;
			dialogWidth = size.x;
			IDialogSettings dialogSettings = getDialogSettings();
			dialogSettings.put(DIALOG_HEIGHT, size.y);
			dialogSettings.put(DIALOG_WIDTH, size.x);
		}
