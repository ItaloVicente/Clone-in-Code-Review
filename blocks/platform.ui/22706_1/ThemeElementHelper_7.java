	public static String createPreferenceKey(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, String id) {
		String cssThemePrefix = cssTheme != null ? cssTheme.getId() + '.' : ""; //$NON-NLS-1$
		return cssThemePrefix + createPreferenceKey(theme, id);
	}

