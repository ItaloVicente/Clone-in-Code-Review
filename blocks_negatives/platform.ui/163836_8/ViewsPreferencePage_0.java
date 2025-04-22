	@Override
	protected void performApply() {
		super.performApply();
		if (engine != null) {
			ITheme theme = getSelectedTheme();
			IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
			boolean themeChanged = theme != null && !theme.equals(currentTheme);
			boolean colorsAndFontsThemeChanged = !PlatformUI.getWorkbench().getThemeManager().getCurrentTheme().getId()
					.equals(currentColorsAndFontsTheme.getId());
			boolean tabCornersChanged = !useRoundTabs.getSelection() != apiStore
					.getBoolean(IWorkbenchPreferenceConstants.USE_ROUND_TABS);

			if (theme != null) {
				currentTheme = theme;
			}

			ColorsAndFontsTheme colorsAndFontsTheme = getSelectedColorsAndFontsTheme();
			if (colorsAndFontsTheme != null) {
				currentColorsAndFontsTheme = colorsAndFontsTheme;
			}

			themeComboDecorator.hide();
			colorFontsDecorator.hide();

			if (themeChanged || colorsAndFontsThemeChanged || tabCornersChanged) {
				MessageDialog.openWarning(getShell(), WorkbenchMessages.ThemeChangeWarningTitle,
						WorkbenchMessages.ThemeChangeWarningText);
			}
		}

	}

