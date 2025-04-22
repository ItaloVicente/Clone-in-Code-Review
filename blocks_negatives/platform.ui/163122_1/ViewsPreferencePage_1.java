	private void refreshColorsAndFontsThemeDescriptionText(ColorsAndFontsTheme theme) {
		if (theme != null) {
			IThemeDescriptor[] descs = WorkbenchPlugin.getDefault().getThemeRegistry().getThemes();
			for (IThemeDescriptor desc : descs) {
				if (desc.getId().equals(theme.getId()) && desc.getDescription() != null) {
					description = desc.getDescription();
					break;
				}
			}
		}
		colorsAndFontsThemeDescriptionText.setText(description);
	}

