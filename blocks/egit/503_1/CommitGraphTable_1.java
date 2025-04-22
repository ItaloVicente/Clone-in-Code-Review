
	private Font getBoldFont(final String id) {
		ITheme theme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
		return theme.getFontRegistry().getBold(id);
	}

	private Font getFont(final String id) {
		ITheme theme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
		return theme.getFontRegistry().get(id);
	}
