			result.add(new ColorsAndFontsTheme(descs[i].getId(), themeString));
		}
		return result;
	}

	private void refreshColorsAndFontsThemeDescriptionText(ColorsAndFontsTheme theme) {
		IThemeDescriptor[] descs = WorkbenchPlugin.getDefault().getThemeRegistry().getThemes();
		
		for (int i = 0; theme != null && description == null && i < descs.length; i++) {
			if (descs[i].getId().equals(theme.getId())) {
				description = descs[i].getDescription();
