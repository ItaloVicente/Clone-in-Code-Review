	private static void setCSSTheme(Display display, IThemeEngine themeEngine,
			String cssTheme) {
		if (display.getHighContrast()) {
			themeEngine.setTheme(cssTheme, false);
		} else {
			themeEngine.restore(cssTheme);
		}
	}

