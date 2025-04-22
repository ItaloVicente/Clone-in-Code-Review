	public static void populateRegistry(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, FontRegistry registry, FontDefinition[] definitions,
			IPreferenceStore store) {
		for (FontDefinition definition : definitions) {
			String key = createPreferenceKey(cssTheme, theme, definition.getId());
			FontData[] prefFont = PreferenceConverter.getFontDataArray(store, key);

			if (isFontOverridden(prefFont)) {
				definition.appendState(ThemeElementDefinition.State.OVERRIDDEN);
				Font currentFont = registry.get(definition.getId());
				if (hasToUpdateRegistry(currentFont, prefFont)) {
					if (isModifiedByUser(definition, currentFont, theme, store)) {
						definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
					}
					registry.put(definition.getId(), prefFont);
				}
			}
		}
	}

	private static boolean isFontOverridden(FontData[] prefFont) {
		return prefFont != null && prefFont != PreferenceConverter.FONTDATA_ARRAY_DEFAULT_DEFAULT;
	}

	private static boolean hasToUpdateRegistry(Font currentFont, FontData[] prefFont) {
		return currentFont == null || currentFont.getFontData() != prefFont;
	}

	private static boolean isModifiedByUser(FontDefinition definition, Font currentFont,
			ITheme theme, IPreferenceStore store) {
		FontData[] defaultFontData = null;
		if (definition.getDefaultsTo() != null) {
			String defaultsToKey = createPreferenceKey(theme, definition.getDefaultsTo());
			defaultFontData = PreferenceConverter.getDefaultFontDataArray(store, defaultsToKey);
		}
		return defaultFontData != null && currentFont != null
				&& !currentFont.getFontData().equals(defaultFontData);
	}

