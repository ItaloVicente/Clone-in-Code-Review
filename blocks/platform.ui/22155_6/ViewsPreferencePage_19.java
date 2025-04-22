	@SuppressWarnings("unchecked")
	private void selectColorsAndFontsTheme(String colorAndFontThemeId) {
		if (colorAndFontThemeId == null) {
			colorAndFontThemeId = currentColorsAndFontsTheme.getId();
		}

		List<ColorsAndFontsTheme> colorsAndFontsThemes = (List<ColorsAndFontsTheme>) colorsAndFontsThemeCombo
				.getInput();

		for (int i = 0; i < colorsAndFontsThemes.size(); i++) {
			if (colorsAndFontsThemes.get(i).getId().equals(colorAndFontThemeId)) {
				colorsAndFontsThemeCombo.getCombo().select(i);
				break;
			}
		}
