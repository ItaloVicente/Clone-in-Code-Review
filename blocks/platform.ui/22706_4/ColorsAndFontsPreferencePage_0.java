	private String createPreferenceKey(ThemeElementDefinition definition) {
		if (isAvailableInCurrentTheme(definition)
				&& (definition.isOverridden() || definition.isAddedByCss())) {
			return ThemeElementHelper.createPreferenceKey(currentCSSTheme, currentTheme,
					definition.getId());
		}
		return ThemeElementHelper.createPreferenceKey(currentTheme, definition.getId());
	}

