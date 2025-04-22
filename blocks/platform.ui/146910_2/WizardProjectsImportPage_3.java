	private static boolean getRebuildProjectAfterImportPreference() {
		IScopeContext[] scopes = { ConfigurationScope.INSTANCE, DefaultScope.INSTANCE };
		IPreferencesService preferencesService = Platform.getPreferencesService();
		String defaultValue = String.valueOf(Boolean.FALSE);
		String rebuildProjectsPreference = preferencesService.getString(IDEWorkbenchPlugin.IDE_WORKBENCH,
				PREF_REBUILD_PROJECTS_AFTER_IMPORT, defaultValue, scopes);
		boolean booleanValue = Boolean.valueOf(rebuildProjectsPreference).booleanValue();
		return booleanValue;
	}
