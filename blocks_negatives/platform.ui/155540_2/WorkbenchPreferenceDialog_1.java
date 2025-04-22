	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
		if (section == null) {
			section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
		}
		return section;
	}

	/**
	 * Overridden to persist only the location, not the size, since the current page
	 * dictates the most appropriate size for the dialog.
	 *
	 * @since 3.2
	 */
	@Override
	protected int getDialogBoundsStrategy() {
		return DIALOG_PERSISTLOCATION;
	}

