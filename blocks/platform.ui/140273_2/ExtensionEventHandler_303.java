		Collection fonts = reader.getFontDefinitions();
		FontDefinition[] fontDefs = (FontDefinition[]) fonts.toArray(new FontDefinition[fonts.size()]);
		ThemeElementHelper.populateRegistry(workbench.getThemeManager().getTheme(IThemeManager.DEFAULT_THEME), fontDefs,
				PrefUtil.getInternalPreferenceStore());
	}

	private void loadThemes(IExtension ext) {
		ThemeRegistryReader reader = new ThemeRegistryReader();
		ThemeRegistry registry = (ThemeRegistry) WorkbenchPlugin.getDefault().getThemeRegistry();
		reader.setRegistry(registry);
