		colorsAndFontsThemeDescriptionText.setLayoutData(layoutData);
	}

	@SuppressWarnings("unchecked")
	private void selectColorsAndFontsTheme(String colorAndFontThemeId) {
		colorsAndFontsThemeCombo.getCombo().setEnabled(colorAndFontThemeId != null);
		if (colorAndFontThemeId == null) {
			colorAndFontThemeId = currentColorsAndFontsTheme.getId();
		}

		List<ColorsAndFontsTheme> colorsAndFontsThemes = (List<ColorsAndFontsTheme>) colorsAndFontsThemeCombo
				.getInput();
