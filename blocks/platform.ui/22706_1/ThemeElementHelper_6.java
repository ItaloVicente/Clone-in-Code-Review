	public static void populateRegistry(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, ColorRegistry registry, ColorDefinition[] definitions,
			IPreferenceStore store) {
		for (ColorDefinition definition : definitions) {
			String key = createPreferenceKey(cssTheme, theme, definition.getId());
			RGB prefColor = PreferenceConverter.getColor(store, key);

			if (isColorOverridden(prefColor)) {
				definition.appendState(ThemeElementDefinition.State.OVERRIDDEN);
				Color currentColor = registry.get(definition.getId());
				if (hasToUpdateRegistry(currentColor, prefColor)) {
					if (isModifiedByUser(definition, currentColor, theme, store)) {
						definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
					}
					registry.put(definition.getId(), prefColor);
				}
			}
		}
	}

	private static boolean isColorOverridden(RGB prefColor) {
		return prefColor != null && prefColor != PreferenceConverter.COLOR_DEFAULT_DEFAULT;
	}

	private static boolean hasToUpdateRegistry(Color currentColor, RGB prefColor) {
		return currentColor == null || currentColor.getRGB() != prefColor;
	}

	private static boolean isModifiedByUser(ColorDefinition definition, Color currentColor,
			ITheme theme, IPreferenceStore store) {
		RGB defaultValue = null;
		if (definition.getDefaultsTo() != null) {
			String defaultsToKey = createPreferenceKey(theme, definition.getDefaultsTo());
			defaultValue = PreferenceConverter.getDefaultColor(store, defaultsToKey);
		}
		return defaultValue != null && currentColor != null
				&& !currentColor.getRGB().equals(defaultValue);
	}

