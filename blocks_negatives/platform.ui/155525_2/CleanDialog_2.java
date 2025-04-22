	/**
	 * Returns the initial size which is the larger of the <code>initialSize</code> or
	 * the size persisted in the IDE UI Plugin dialog settings under the provided dialog setttings section name.
	 * If no size is persisted in the settings, the <code>initialSize</code> is returned.
	 *
	 * @param initialSize The initialSize to compare against
	 * @param dialogSettingsSectionName The name of the dialog settings section
	 * @return the initial size
	 */
	private Point getInitialSize(String dialogSettingsSectionName, Point initialSize) {
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		try {
			int x, y;
			x = settings.getInt(DIALOG_WIDTH);
			y = settings.getInt(DIALOG_HEIGHT);
			return new Point(Math.max(x, initialSize.x), Math.max(y, initialSize.y));
		} catch (NumberFormatException e) {
		}
		return initialSize;
	}

