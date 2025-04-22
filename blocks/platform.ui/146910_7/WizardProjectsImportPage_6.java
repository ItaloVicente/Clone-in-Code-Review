	private static boolean getRebuildProjectAfterImportPreference() {
		IScopeContext[] scopes = { null };
		IPreferencesService preferencesService = Platform.getPreferencesService();
		boolean defaultValue = false;
		boolean rebuildProjectsPreference = preferencesService.getBoolean(IDEWorkbenchPlugin.IDE_WORKBENCH,
				PREF_REBUILD_PROJECTS_AFTER_IMPORT, defaultValue, scopes);
		return rebuildProjectsPreference;
	}
