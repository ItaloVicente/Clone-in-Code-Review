
	private void readRenameModeFromPreferences() {
		IPreferenceStore store = getPreferenceStore();
		String renameMode = store.getString(IWorkbenchPreferenceConstants.RESOURCE_RENAME_MODE);
		renameModeInline = !IWorkbenchPreferenceConstants.RESOURCE_RENAME_MODE_DIALOG.equals(renameMode);
	}
