	/**
	 * This method will ensure that the fonts and colors used by the decorator
	 * are cached in the registries. This avoids having to syncExec when
	 * decorating since we ensure that the fonts and colors are pre-created.
	 */
	private void ensureFontAndColorsCreated() {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(() -> {
			ITheme theme = PlatformUI.getWorkbench().getThemeManager()
					.getCurrentTheme();
			for (String actColor : COLOR_IDS) {
				theme.getColorRegistry().get(actColor);

			}
			for (String actFont : FONT_IDS) {
				theme.getFontRegistry().get(actFont);
			}
			theme.getFontRegistry().defaultFont();
			defaultBackgroundRgb = display
					.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
		});
	}

