
	private boolean showDetailedFailureDialog() {
		return Activator
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						UIPreferences.CLONE_WIZARD_SHOW_DETAILED_FAILURE_DIALOG);
	}

