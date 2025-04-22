		if (setInRegistry) {
			registry.put(id, prefColor);
		}

		if (store != null) {
			PreferenceConverter.setDefault(store, key, defaultColor);
		}
	}

	public static String createPreferenceKey(ITheme theme, String id) {
		String themeId = theme.getId();
		if (themeId.equals(IThemeManager.DEFAULT_THEME)) {
