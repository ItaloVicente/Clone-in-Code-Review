	private static final String TEAM_UI_PLUGIN_ID = "org.eclipse.team.ui"; //$NON-NLS-1$

	private static final String TEAM_UI_LINKING_PREFERENCE = "pref_generichistory_view_linking"; //$NON-NLS-1$

	public static boolean isLinkingEnabled() {
		return Platform.getPreferencesService().getBoolean(TEAM_UI_PLUGIN_ID,
				TEAM_UI_LINKING_PREFERENCE, false, null);
	}

