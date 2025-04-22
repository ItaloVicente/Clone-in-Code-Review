	private AutoCloseable setPageSetting(WizardProjectsImportPage wpip, String settingName, boolean settingValue) {
		IDialogSettings dialogSettings = wpip.getWizard().getDialogSettings();
		wpip.saveWidgetValues();
		boolean originalValue = dialogSettings.getBoolean(settingName);
		dialogSettings.put(settingName, settingValue);
		wpip.restoreWidgetValues();
		return () -> {
			dialogSettings.put(settingName, originalValue);
			wpip.restoreWidgetValues();
		};
	}

