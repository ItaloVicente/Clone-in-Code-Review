		boolean autoBuild = ResourcesPlugin.getPlugin().getPluginPreferences()
				.getDefaultBoolean(ResourcesPlugin.PREF_AUTO_BUILDING);
		autoBuildButton.setSelection(autoBuild);

		IPreferenceStore store = getIDEPreferenceStore();
		autoSaveAllButton.setSelection(store.getDefaultBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD));
		saveInterval.loadDefault();

		boolean showLocationPath = store.getDefaultBoolean(IDEInternalPreferences.SHOW_LOCATION);
		boolean showLocationName = !showLocationPath;
		showLocationPathInTitle.setSelection(showLocationPath);
		showLocationNameInTitle.setSelection(showLocationName);
		workspaceName.loadDefault();

		boolean closeUnrelatedProj = store.getDefaultBoolean(IDEInternalPreferences.CLOSE_UNRELATED_PROJECTS);
		closeUnrelatedProjectButton.setSelection(closeUnrelatedProj);

		boolean lightweightRefresh = ResourcesPlugin.getPlugin().getPluginPreferences()
				.getDefaultBoolean(ResourcesPlugin.PREF_LIGHTWEIGHT_AUTO_REFRESH);
		boolean autoRefresh = ResourcesPlugin.getPlugin().getPluginPreferences()
				.getDefaultBoolean(ResourcesPlugin.PREF_AUTO_REFRESH);
		autoRefreshButton.setSelection(autoRefresh);
		lightweightRefreshButton.setSelection(lightweightRefresh);

		clearUserSettings = true;
