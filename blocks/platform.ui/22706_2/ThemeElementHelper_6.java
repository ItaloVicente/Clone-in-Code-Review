	public static void populateRegistry(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, ColorRegistry registry, ColorDefinition[] definitions,
			IPreferenceStore store) {
		for (ColorDefinition definition : definitions) {
			if (!(definition.isOverridden() || definition.isAddedByCss())) {
				continue;
			}
			String key = createPreferenceKey(cssTheme, theme, definition.getId());
			String value = store.getString(key);
			if (!IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
				definition.appendState(ThemeElementDefinition.State.OVERRIDDEN);
				definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
				registry.put(definition.getId(), StringConverter.asRGB(value));
			}
		}
	}

