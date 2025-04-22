	private void refreshThemeDescriptionText() {
		String description = null;
		int idx = themeCombo.getSelectionIndex();
		if (idx > 0) {
			IThemeDescriptor theme = WorkbenchPlugin.getDefault().getThemeRegistry().getThemes()[idx - 1];
			description = theme.getDescription();
