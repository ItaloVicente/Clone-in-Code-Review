		return themeRegistry.findColor(defaultsTo);
	}

	private RGB getColorAncestorValue(ColorDefinition definition) {
		ColorDefinition ancestor = getColorAncestor(definition);
		if (ancestor == null)
