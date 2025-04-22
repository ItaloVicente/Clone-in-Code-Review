	/**
	 * Return whether or not dialogs should be run in the background
	 *
	 * @return <code>true</code> if the dialog should not be shown.
	 */
	private boolean shouldRunInBackground() {
		return WorkbenchPlugin.getDefault().getPreferenceStore().getBoolean(
				IPreferenceConstants.RUN_IN_BACKGROUND);
	}
