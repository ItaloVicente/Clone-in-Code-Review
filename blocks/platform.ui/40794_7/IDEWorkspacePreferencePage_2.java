		IPreferenceStore store = getIDEPreferenceStore();

		boolean autoSave = ResourcesPlugin.getPlugin().getPluginPreferences()
				.getDefaultBoolean(IDEInternalPreferences.SAVE_AUTOMATICALLY);
		autoSaveButton.setSelection(autoSave);

        autoSaveBeforeBuildButton
