	/**
	 * Return whether or not dialogs should be run in the background
	 *
	 * @return <code>true</code> if the dialog should not be shown.
	 */
	protected boolean shouldRunInBackground() {
		return Preferences.getBoolean(IProgressConstants.RUN_IN_BACKGROUND);
	}

