
	@Override
	public boolean getCustomThemeFlag() {
		IThemeEngine engine = e4Context.get(IThemeEngine.class);
		if (engine != null) {
			return !engine.getActiveTheme().getId().equals(DEFAULT_THEME);
		}
		return false;
	}
