	private boolean isAutoStageOnCommitEnabled() {
		IPreferenceStore uiPreferences = Activator.getDefault()
				.getPreferenceStore();
		return uiPreferences.getBoolean(UIPreferences.AUTO_STAGE_ON_COMMIT);
	}

