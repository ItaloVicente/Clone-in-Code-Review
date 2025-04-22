	protected IDialogSettings getDialogSettings() {
		final IDialogSettings workbenchDialogSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings result = workbenchDialogSettings.getSection(getId());
		if (result == null) {
			result = workbenchDialogSettings.addNewSection(getId());
		}
		return result;
	}

