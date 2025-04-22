	public static void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, ColorRegistry registry, ColorDefinition definition, IPreferenceStore store) {
		String key = createPreferenceKey(cssTheme, theme, definition.getId());
		String value = store.getString(key);
		if (!IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
			definition.appendState(ThemeElementDefinition.State.OVERRIDDEN);
			definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
			registry.put(definition.getId(), StringConverter.asRGB(value));
		}
	}

