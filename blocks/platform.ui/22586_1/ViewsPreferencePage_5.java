		int idx = themeCombo.getSelectionIndex();
		if (idx <= 0) {
			PlatformUI.getWorkbench().getThemeManager()
					.setCurrentTheme(IThemeManager.DEFAULT_THEME);
			refreshThemeCombo(IThemeManager.DEFAULT_THEME);
		} else {
			IThemeDescriptor applyTheme = WorkbenchPlugin.getDefault().getThemeRegistry()
					.getThemes()[idx - 1];
			PlatformUI.getWorkbench().getThemeManager().setCurrentTheme(applyTheme.getId());
			refreshThemeCombo(applyTheme.getId());
		}
		refreshThemeDescriptionText();
