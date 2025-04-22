	private void testColorRegistryPut(ITheme theme, IPreferenceStore store, String color) {
		String key = ThemeElementHelper.createPreferenceKey(theme, color);
		RGB oldRGB = PreferenceConverter.getColor(store, key);
		RGB newRGB = new RGB(57, 12, 86);

		theme.getColorRegistry().put(color, newRGB);
		assertEquals(newRGB, store.isDefault(key) ? PreferenceConverter.getDefaultColor(store, key)
				: PreferenceConverter.getColor(store, key));
		theme.getColorRegistry().put(color, oldRGB);
		assertEquals(oldRGB, store.isDefault(key) ? PreferenceConverter.getDefaultColor(store, key)
				: PreferenceConverter.getColor(store, key));
	}

