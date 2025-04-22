		List<ColorsAndFontsTheme> colorsAndFontsThemes = (List<ColorsAndFontsTheme>) colorsAndFontsThemeCombo
				.getInput();

		for (int i = 0; i < colorsAndFontsThemes.size(); i++) {
			if (colorsAndFontsThemes.get(i).getId().equals(colorAndFontThemeId)) {
				ISelection selection = new StructuredSelection(colorsAndFontsThemes.get(i));
