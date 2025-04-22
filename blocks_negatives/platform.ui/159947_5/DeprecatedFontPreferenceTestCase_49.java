		storedValue[1] = (PreferenceConverter.getDefaultFontDataArray(
				preferenceStore, JFaceResources.TEXT_FONT))[0];
		PreferenceConverter
				.setValue(preferenceStore, TEST_FONT_ID, storedValue);
		PreferenceConverter.setDefault(preferenceStore, TEST_FONT_ID,
				storedValue);
