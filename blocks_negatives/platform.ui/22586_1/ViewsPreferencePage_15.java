		ITheme theme = getSelectedTheme();
		if (theme != null) {
			engine.setTheme(getSelectedTheme(), true);
		}

		boolean themeChanged = theme != null && !theme.equals(currentTheme);
		boolean colorsAndFontsThemeChanged = !PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getId().equals(currentColorsAndFontsTheme.getId());

		if (themeChanged || colorsAndFontsThemeChanged) {
			MessageDialog.openWarning(getShell(), WorkbenchMessages.ThemeChangeWarningTitle,
					WorkbenchMessages.ThemeChangeWarningText);
