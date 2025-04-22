		String modelProvider;
		if (Activator
				.getDefault()
				.getPreferenceStore()
				.getBoolean(UIPreferences.SYNC_VIEW_ALWAYS_SHOW_CHANGESET_MODEL))
			modelProvider = GitChangeSetModelProvider.ID;
		else
			modelProvider = WORKSPACE_MODEL_PROVIDER_ID;
