	private AutoCloseable setPageSetting(WizardProjectsImportPage wpip, String settingName, boolean settingValue) {
		wpip.saveWidgetValues();
		IDialogSettings dialogSettings = wpip.getWizard().getDialogSettings();
		boolean originalValue = dialogSettings.getBoolean(settingName);
		dialogSettings.put(settingName, settingValue);
		wpip.restoreWidgetValues();
		return () -> {
			dialogSettings.put(settingName, originalValue);
			wpip.restoreWidgetValues();
		};
	}

