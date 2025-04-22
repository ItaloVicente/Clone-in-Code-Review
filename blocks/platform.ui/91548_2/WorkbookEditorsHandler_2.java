	private static boolean isCustomThemeUsed() {
		IThemeEngine engine = PlatformUI.getWorkbench().getService(IThemeEngine.class);
		if (engine != null) {
			ITheme activeTheme = engine.getActiveTheme();
			if (activeTheme != null) {
				return !DEFAULT_THEME.equals(activeTheme.getId());
			}
		}
		return false;
	}

