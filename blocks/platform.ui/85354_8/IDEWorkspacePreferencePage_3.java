		IPreferenceStore store = getIDEPreferenceStore();
		autoSaveAllButton.setSelection(store.getDefaultBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD));
		saveInterval.loadDefault();

		boolean showLocationPath = store.getDefaultBoolean(IDEInternalPreferences.SHOW_LOCATION);
		boolean showLocationName = !showLocationPath;
		showLocationPathInTitle.setSelection(showLocationPath);
		showLocationNameInTitle.setSelection(showLocationName);
		workspaceName.loadDefault();
