		return new FontDefinition(name, id, defaultMapping, value, categoryId, isEditable, description);
	}

	private String getPlatformSpecificFontValue(IConfigurationElement[] elements) {
		return getFontValue(getBestPlatformMatch(elements));
	}

	private String getFontValue(IConfigurationElement element) {
		if (element == null) {
