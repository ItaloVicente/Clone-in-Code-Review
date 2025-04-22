	private void refreshDiffFonts() {
		FontRegistry reg = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getFontRegistry();
		this.headlineFont = reg.get(THEME_DiffHeadlineFont);
	}

