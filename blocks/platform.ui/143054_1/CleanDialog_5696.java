		Point p = super.getInitialSize();
		return getInitialSize(DIALOG_SETTINGS_SECTION, p);
	}

	public Point getInitialLocation(String dialogSettingsSectionName) {
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		try {
			int x= settings.getInt(DIALOG_ORIGIN_X);
			int y= settings.getInt(DIALOG_ORIGIN_Y);
			return new Point(x,y);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	private IDialogSettings getDialogSettings(String dialogSettingsSectionName) {
		IDialogSettings settings = IDEWorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(dialogSettingsSectionName);
		if (section == null) {
			section = settings.addNewSection(dialogSettingsSectionName);
		}
		return section;
	}

	private void persistDialogSettings(Shell shell, String dialogSettingsSectionName) {
		Point shellLocation = shell.getLocation();
		Point shellSize = shell.getSize();
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		settings.put(DIALOG_ORIGIN_X, shellLocation.x);
		settings.put(DIALOG_ORIGIN_Y, shellLocation.y);
		settings.put(DIALOG_WIDTH, shellSize.x);
		settings.put(DIALOG_HEIGHT, shellSize.y);

		if (buildNowButton != null) {
			settings.put(BUILD_NOW, buildNowButton.getSelection());
		}
		if (globalBuildButton != null) {
			settings.put(BUILD_ALL, globalBuildButton.getSelection());
		}
