	@Override
	protected Point getInitialLocation(Point initialSize) {
		Point p = getInitialLocation(DIALOG_SETTINGS_SECTION);
		return p != null ? p : super.getInitialLocation(initialSize);
	}

	@Override
	protected Point getInitialSize() {
		Point p = super.getInitialSize();
		return getInitialSize(DIALOG_SETTINGS_SECTION, p);
	}

	/**
	 * Returns the initial location which is persisted in the IDE Plugin dialog settings
	 * under the provided dialog setttings section name.
	 * If location is not persisted in the settings, the <code>null</code> is returned.
	 *
	 * @param dialogSettingsSectionName The name of the dialog settings section
	 * @return The initial location or <code>null</code>
	 */
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
