	private void restoreSashFormWeights() {
		restoreSashFormWeights(horizontalSashForm,
				HORIZONTAL_SASH_FORM_WEIGHT);
		restoreSashFormWeights(stagingSashForm,
				STAGING_SASH_FORM_WEIGHT);
	}

	private void restoreSashFormWeights(SashForm sashForm, String settingsKey) {
		IDialogSettings settings = getDialogSettings();
		String weights = settings.get(settingsKey);
