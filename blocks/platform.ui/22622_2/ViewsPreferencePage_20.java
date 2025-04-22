		colorsAndFontsThemeDescriptionText.setLayoutData(layoutData);
	}

	@SuppressWarnings("unchecked")
	private void selectColorsAndFontsTheme(String colorAndFontThemeId) {
		if (colorAndFontThemeId == null) {
			colorAndFontThemeId = currentColorsAndFontsTheme.getId();
		}

		List<ColorsAndFontsTheme> colorsAndFontsThemes = (List<ColorsAndFontsTheme>) colorsAndFontsThemeCombo
				.getInput();
