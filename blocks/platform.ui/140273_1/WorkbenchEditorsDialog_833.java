		s.put(COLUMNS, array);
	}

	private IDialogSettings getDialogSettings() {
		IDialogSettings settings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings thisSettings = settings.getSection(getClass().getName());
		if (thisSettings == null) {
