
		resetPreferences();
	}

	private void resetPreferences() {
		IPreferenceStore prefStore = Activator.getDefault()
				.getPreferenceStore();
		prefStore.setValue(FEATURE_FINISH_SQUASH, false);
		prefStore.setValue(FEATURE_FINISH_KEEP_BRANCH, false);
