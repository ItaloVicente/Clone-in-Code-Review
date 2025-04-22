	private static boolean isCustomThemeUsed() {
		IThemeEngine engine = PlatformUI.getWorkbench().getService(IThemeEngine.class);
		if (engine != null) {
			ITheme activeTheme = engine.getActiveTheme();
			if (activeTheme != null) {
				return !(activeTheme.getId().startsWith(DEFAULT_THEME));
			}
		}
		return false;
	}

