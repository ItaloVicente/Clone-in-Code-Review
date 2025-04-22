	private void repopulateThemeRegistries(ThemeRegistry themeRegistry, FontRegistry fontRegistry,
			ColorRegistry colorRegistry, org.eclipse.e4.ui.css.swt.theme.ITheme theme,
			ITheme colorsAndFontsTheme) {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		if (theme != null && store != null) {
			ThemeElementHelper.populateRegistry(theme, colorsAndFontsTheme, colorRegistry,
					themeRegistry.getColors(), store);

			ThemeElementHelper.populateRegistry(theme, colorsAndFontsTheme, fontRegistry,
					themeRegistry.getFonts(), store);
		}
	}

