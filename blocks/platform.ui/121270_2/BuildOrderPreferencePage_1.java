
		boolean autoBuild = ResourcesPlugin.getPlugin().getPluginPreferences()
				.getDefaultBoolean(ResourcesPlugin.PREF_AUTO_BUILDING);
		autoBuildButton.setSelection(autoBuild);

		int simultaneousBuilds = ResourcesPlugin.getPlugin().getPluginPreferences()
				.getDefaultInt(ResourcesPlugin.PREF_MAX_CONCURRENT_BUILDS);
		maxSimultaneousBuilds.setStringValue(Integer.toString(simultaneousBuilds));

		IPreferenceStore store = getIDEPreferenceStore();
		autoSaveAllButton.setSelection(store.getDefaultBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD));
