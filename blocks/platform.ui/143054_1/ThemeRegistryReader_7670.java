		return new ColorDefinition(name, id, defaultMapping, value, categoryId, isEditable, description,
				element.getDeclaringExtension().getContributor().getName());
	}

	private String getColorValue(IConfigurationElement element) {
		if (element == null) {
