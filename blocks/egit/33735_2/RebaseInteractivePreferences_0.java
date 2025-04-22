
	public static boolean isReactOnSelection() {
		IPreferenceStore preferenceStore = getPreferencesStore();
		if (!preferenceStore
				.contains(UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION))
			preferenceStore.setDefault(
					UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION, true);

		return preferenceStore
				.getBoolean(UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION);
	}

	public static void setReactOnSelection(boolean react) {
		getPreferencesStore().setValue(
				UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION, react);
	}
