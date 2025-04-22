
		ensureFontAndColorsCreated(fonts, colors);
	}

	private void ensureFontAndColorsCreated(final String[] fonts, final String[] colors) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				ITheme theme  = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
				for (int i = 0; i < colors.length; i++) {
					theme.getColorRegistry().get(colors[i]);

				}
				for (int i = 0; i < fonts.length; i++) {
					theme.getFontRegistry().get(fonts[i]);
				}
			}
		});
