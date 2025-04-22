	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings section = settings
				.getSection(DIALOG_SETTINGS_SECTION_NAME);
		if (section == null)
			section = settings.addNewSection(DIALOG_SETTINGS_SECTION_NAME);
		return section;
	}

