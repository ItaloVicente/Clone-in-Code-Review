		if (!gsds.containsFolderLevelSynchronizationRequest()) {
			if (preferenceStore
					.getBoolean(UIPreferences.SYNC_VIEW_ALWAYS_SHOW_CHANGESET_MODEL)) {
				modelProvider = GitChangeSetModelProvider.ID;
			} else {
				String lastSelectedModel = preferenceStore.getString(UIPreferences.SYNC_VIEW_LAST_SELECTED_MODEL);
				if (!"".equals(lastSelectedModel)) //$NON-NLS-1$
					modelProvider = lastSelectedModel;
			}
