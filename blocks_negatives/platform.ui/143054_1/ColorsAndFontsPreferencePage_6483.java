    private FontData[] getFontAncestorValue(FontDefinition definition) {
        FontDefinition ancestor = getFontAncestor(definition);
        if (ancestor == null) {
			return PreferenceConverter.getDefaultFontDataArray(
getPreferenceStore(),
					createPreferenceKey(definition));
