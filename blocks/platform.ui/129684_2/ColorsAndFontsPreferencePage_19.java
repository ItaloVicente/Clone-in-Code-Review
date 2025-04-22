	}

	protected void setRegistryValue(ColorDefinition definition, RGB newRGB) {
		colorRegistry.put(definition.getId(), newRGB);
	}

	protected void setRegistryValue(FontDefinition definition, FontData[] datas) {
		fontRegistry.put(definition.getId(), datas);
	}

	private IThemePreview getThemePreview(ThemeElementCategory category) throws CoreException {
		IThemePreview preview = category.createPreview();
		if (preview != null)
