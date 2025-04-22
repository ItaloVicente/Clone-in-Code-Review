
	private void readRenameModeFromPreferences() {
		IPreferenceStore store = getPreferenceStore();
		String renameMode = store.getString(IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE_DIALOG);
		if (renameMode == null || renameMode.isEmpty()) {
			renameMode = getRenameModePreference();
		}
		renameModeInline = !IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE_DIALOG.equals(renameMode);
	}

	private static String getRenameModePreference() {
		IPreferencesService preferences = Platform.getPreferencesService();
		String renameMode = preferences.getString(WORKBENCH_PLUGIN_ID,
				IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE, "", //$NON-NLS-1$
				null);
		return renameMode;
	}
