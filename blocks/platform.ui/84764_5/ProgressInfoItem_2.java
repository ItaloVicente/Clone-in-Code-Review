
	private boolean getCustomThemeFlag() {
		IThemeEngine engine = PlatformUI.getWorkbench().getService(IThemeEngine.class);
		if (engine != null) {
			return !engine.getActiveTheme().getId().equals(DEFAULT_THEME);
		}
		return false;
	}
