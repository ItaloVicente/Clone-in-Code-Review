		IThemeDescriptor desc = findTheme(themeId);
		FontDefinition[] overrides = desc.getFonts();
		return (FontDefinition[]) overlay(defs, overrides);
	}

	private IThemeElementDefinition[] overlay(IThemeElementDefinition[] defs, IThemeElementDefinition[] overrides) {
		for (IThemeElementDefinition override : overrides) {
