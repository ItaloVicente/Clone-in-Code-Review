	@Override
	protected void performApply() {
		super.performApply();

		ITheme theme = getSelectedTheme();
		if (theme != null) {
			currentTheme = theme;
		}

		ColorsAndFontsTheme colorsAndFontsTheme = getSelectedColorsAndFontsTheme();
		if (colorsAndFontsTheme != null) {
			currentColorsAndFontsTheme = colorsAndFontsTheme;
		}
	}

	private void createColorsAndFontsThemeCombo(Composite composite) {
