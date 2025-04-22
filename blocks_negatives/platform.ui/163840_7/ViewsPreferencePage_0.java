	@Override
	public boolean performCancel() {
		if (engine != null) {
			setColorsAndFontsTheme(currentColorsAndFontsTheme);

			if (currentTheme != null) {
				engine.setTheme(currentTheme, false);
			}
		}

		return super.performCancel();
	}

