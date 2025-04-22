	/**
	 * Persists the location and dimensions of the shell and other user settings in the
	 * plugin's dialog settings under the provided dialog settings section name
	 *
	 * @param shell The shell whose geometry is to be stored
	 * @param dialogSettingsSectionName The name of the dialog settings section
	 */
	private void persistDialogSettings(Shell shell, String dialogSettingsSectionName) {
		Point shellLocation = shell.getLocation();
		Point shellSize = shell.getSize();
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		settings.put(DIALOG_ORIGIN_X, shellLocation.x);
		settings.put(DIALOG_ORIGIN_Y, shellLocation.y);
		settings.put(DIALOG_WIDTH, shellSize.x);
		settings.put(DIALOG_HEIGHT, shellSize.y);
