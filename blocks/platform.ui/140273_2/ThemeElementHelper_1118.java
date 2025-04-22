	public static void populateRegistry(ITheme theme, FontDefinition[] definitions, IPreferenceStore store) {
		FontDefinition[] copyOfDefinitions = null;

		FontDefinition[] defaults = null;
		if (!theme.getId().equals(IThemeManager.DEFAULT_THEME)) {
			definitions = addDefaulted(definitions);
			if (store != null) {
