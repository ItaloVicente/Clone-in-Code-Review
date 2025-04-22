	private boolean defaultIsEditable(IHierarchalThemeElementDefinition definition) {
		assert definition instanceof ColorDefinition || definition instanceof FontDefinition;
		String defaultId = definition.getDefaultsTo();
		if (defaultId == null) {
			return false;
		}
		IEditable defaultDefinition = null;
		if (definition instanceof ColorDefinition) {
			defaultDefinition = themeRegistry.findColor(defaultId);
		} else if (definition instanceof FontDefinition) {
			defaultDefinition = themeRegistry.findFont(defaultId);
		}
		return (defaultDefinition != null && defaultDefinition.isEditable());
	}

