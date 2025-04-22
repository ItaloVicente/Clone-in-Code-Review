		themeListener = event -> {
			String property = event.getProperty();
			if (IThemeManager.CHANGE_CURRENT_THEME.equals(property)
					|| UIPreferences.THEME_CommitMessageFont.equals(property)) {
				Font themeFont = UIUtils
						.getFont(UIPreferences.THEME_CommitMessageFont);
				async(() -> setFont(themeFont));
