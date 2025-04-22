		try {
			ICommandService cmdService = this.serviceLocator.getService(ICommandService.class);
			Command command = cmdService.getCommand(commandId);
			String name = command.getName();
			String description = command.getDescription();
			openPopup(formattedShortcut, name, description);
		} catch (NotDefinedException ignore) {
		}
	}

	private String getFormattedShortcut(Event trigger) {
		int accelerator = SWTKeySupport.convertEventToUnmodifiedAccelerator(trigger);
		KeyStroke keyStroke = SWTKeySupport.convertAcceleratorToKeyStroke(accelerator);
		if (KeyStroke.NO_KEY != keyStroke.getNaturalKey()) {
			return SWTKeySupport.getKeyFormatterForPlatform().format(keyStroke);
		}
		return null;
	}

	private void openPopup(String shortcut, String shortcutText, String shortcutDescription) {
		closePopup();
		int timeToClose = this.preferenceStore.getInt(IPreferenceConstants.SHOW_KEYS_TIME_TO_CLOSE);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		this.shortcutPopup = new ShowKeysPopup(shell, timeToClose);
		this.shortcutPopup.setShortcut(shortcut, shortcutText, shortcutDescription);
		this.shortcutPopup.open();
	}

	private void closePopup() {
		if (this.shortcutPopup != null) {
			this.shortcutPopup.close();
			this.shortcutPopup = null;
		}
