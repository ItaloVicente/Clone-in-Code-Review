
	@Override
	protected int getDialogBoundsStrategy() {
		return DIALOG_PERSISTLOCATION;
	}

	private IDialogSettings getDialogSettings() {
		IDialogSettings dialogSettingsSection  = null;
		if(PlatformUI.isWorkbenchRunning()) {
			IDialogSettings dialogSettings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
			dialogSettingsSection = dialogSettings.getSection(DIALOG_SETTINGS_SECTION);
			if (dialogSettingsSection == null) {
				dialogSettingsSection = dialogSettings.addNewSection(DIALOG_SETTINGS_SECTION);
			}
		}
		return dialogSettingsSection;
	}
