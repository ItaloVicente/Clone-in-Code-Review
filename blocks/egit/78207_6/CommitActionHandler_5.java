			if (uiPreferences.getBoolean(UIPreferences.AUTO_STAGE_ON_COMMIT)) {
				boolean includeUntracked = uiPreferences.getBoolean(
						UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED);
				autoStage(repository, includeUntracked,
						getResourcesInScope(event));
			}
