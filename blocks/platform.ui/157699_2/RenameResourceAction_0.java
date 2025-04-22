
		String defaultValue = ""; //$NON-NLS-1$
		IPreferencesService preferences = Platform.getPreferencesService();
		String renameMode = preferences.getString(WORKBENCH_PLUGIN_ID,
				IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE, defaultValue,
				null);
		boolean dialogMode = IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE_DIALOG.equals(renameMode);

		if (LTKLauncher.isCompositeRename(getStructuredSelection()) || this.navigatorTree == null || dialogMode) {
