			return ""; //$NON-NLS-1$
		}
	};

	ThemeElementCategory findCategory(String id);

	ColorDefinition findColor(String id);

	FontDefinition findFont(String id);

	IThemeDescriptor findTheme(String id);

	ThemeElementCategory[] getCategories();

	ColorDefinition[] getColors();

	ColorDefinition[] getColorsFor(String themeId);

	FontDefinition[] getFontsFor(String themeId);

	FontDefinition[] getFonts();

	IThemeDescriptor[] getThemes();

	Map getData();

	Set getPresentationsBindingsFor(ThemeElementCategory category);
