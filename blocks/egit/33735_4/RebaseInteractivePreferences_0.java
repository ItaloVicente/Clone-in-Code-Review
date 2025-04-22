
	public static boolean isReactOnSelection() {
		return getPreferencesStore().getBoolean(
				UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION);
	}

	public static void setReactOnSelection(boolean react) {
		getPreferencesStore().setValue(
				UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION, react);
	}
