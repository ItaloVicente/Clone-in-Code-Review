        IPreferenceStore store = getIDEPreferenceStore();
        autoSaveAllButton
                .setSelection(store
                        .getDefaultBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD));
        saveInterval.loadDefault();
		showLocationInWindowTitle.setSelection(store.getDefaultBoolean(IDEInternalPreferences.SHOW_LOCATION));
        workspaceName.loadDefault();
