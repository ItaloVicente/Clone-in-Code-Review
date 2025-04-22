			try {
				((PreferencePageEnhancer) Tweaklets.get(PreferencePageEnhancer.KEY))
						.setSelection(selection);
			} catch (SWTException e) {
				WorkbenchPlugin.log("Failed to set CSS preferences", e); //$NON-NLS-1$
			}
			selectColorsAndFontsTheme(getColorAndFontThemeIdByThemeId(selection.getId()));
