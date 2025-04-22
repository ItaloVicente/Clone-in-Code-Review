		if (preferenceStore
				.getBoolean(UIPreferences.SYNC_VIEW_ALWAYS_SHOW_CHANGESET_MODEL)) {
			modelProvider = GitChangeSetModelProvider.ID;
		} else {
			String lastSelectedModel = preferenceStore.getString(UIPreferences.SYNC_VIEW_LAST_SELECTED_MODEL);
				modelProvider = lastSelectedModel;
			else
				modelProvider = WORKSPACE_MODEL_PROVIDER_ID;
