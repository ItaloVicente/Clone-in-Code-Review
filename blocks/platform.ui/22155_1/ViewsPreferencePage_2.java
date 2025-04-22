		if (currentTheme != null) {
			String colorsAndFontsThemeId = getColorAndFontThemeIdByThemeId(currentTheme
					.getId());
			if (colorsAndFontsThemeId != null
					&& !currentColorsAndFontsTheme.getId().equals(colorsAndFontsThemeId)) {
				colorsAndFontsThemeId = currentColorsAndFontsTheme.getId();
			}
			selectColorsAndFontsTheme(colorsAndFontsThemeId);
		}
