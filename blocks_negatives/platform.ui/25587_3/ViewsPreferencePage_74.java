	private List<ITheme> getCSSThemes(boolean highContrastMode) {
		List<ITheme> themes = new ArrayList<ITheme>();
		for (ITheme theme : engine.getThemes()) {
			/*
			 * When we have Win32 OS - when the high contrast mode is enabled on
			 * the platform, we display the 'high-contrast' special theme only.
			 * If not, we don't want to mess the themes combo with the theme
			 * since it is the special variation of the 'classic' one
			 * 
			 * When we have GTK - we have to display the entire list of the
			 * themes since we are not able to figure out if the high contrast
			 * mode is enabled on the platform. The user has to manually select
			 * the theme if they need it
			 */
			if (!highContrastMode && !Util.isGtk()
					&& theme.getId().equals(E4Application.HIGH_CONTRAST_THEME_ID)) {
				continue;
			}
			themes.add(theme);
		}
		return themes;
	}

