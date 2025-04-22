		closePopup();
		int timeToClose = this.preferenceStore.getInt(IPreferenceConstants.SHOW_KEYS_TIME_TO_CLOSE);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		this.shortcutPopup = new ShowKeysPopup(shell, timeToClose);
		this.shortcutPopup.setShortcut(shortcut, shortcutText, shortcutDescription);
		this.shortcutPopup.open();
