	private List<ITheme> getCSSThemes(boolean highContrastMode) {
		List<ITheme> themes = new ArrayList<ITheme>();
		for (ITheme theme : engine.getThemes()) {
			if (!highContrastMode && theme.getId().equals(E4Application.HIGH_CONTRAST_THEME_ID)) {
				continue;
			}
			themes.add(theme);
		}
		return themes;
	}

