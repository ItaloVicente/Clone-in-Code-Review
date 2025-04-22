	@SuppressWarnings("restriction")
	private static void disablePerspectiveSwitchPrompt() {
		TeamUIPlugin.getPlugin().getPreferenceStore()
				.setValue(SYNCHRONIZING_COMPLETE_PERSPECTIVE, NEVER);
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH, false);
	}

