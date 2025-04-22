
	private static Font getFont(final String id) {
		return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getFontRegistry().get(id);
	}

	private static Font getBoldFont(final String id) {
		return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getFontRegistry().getBold(id);
	}
