		return this.patterns;

	}

	private void initializeFromPreferences() {
		IPreferenceStore viewsPrefs = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		String storedPatterns = viewsPrefs.getString(FILTERS_TAG);

		if (storedPatterns.length() == 0) {
			IPreferenceStore workbenchPrefs = PrefUtil.getInternalPreferenceStore();
			storedPatterns = workbenchPrefs.getString(FILTERS_TAG);
			if (storedPatterns.length() > 0) {
				viewsPrefs.setValue(FILTERS_TAG, storedPatterns);
				workbenchPrefs.setValue(FILTERS_TAG, ""); //$NON-NLS-1$
			}
		}

		if (storedPatterns.length() == 0) {
