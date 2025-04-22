	protected IDialogSettings getDialogSettings() {
		IDialogSettings workbenchSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection(DIALOG_SETTING_SECTION_NAME);
		if (section == null) {
			section = workbenchSettings.addNewSection(DIALOG_SETTING_SECTION_NAME);
		}
		return section;
	}
