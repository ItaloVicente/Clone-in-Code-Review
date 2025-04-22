	private void initDialogSettingsSection() {
		IDialogSettings dialogSettings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
		dialogSettingsSection = dialogSettings.getSection(DIALOG_SETTINGS_SECTION);
		if (dialogSettingsSection == null) {
			dialogSettingsSection = dialogSettings.addNewSection(DIALOG_SETTINGS_SECTION);
		}
	}

