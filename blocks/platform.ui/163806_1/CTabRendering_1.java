	private void cornerRadiusPreferenceChanged() {
		IEclipsePreferences preferences = getSwtRendererPreferences();
		boolean useRound = preferences.getBoolean(USE_ROUND_TABS, USE_ROUND_TABS_DEFAULT);
		setCornerRadius(useRound ? 14 : 0);
	}

