	@Override
	public void restore(String alternateTheme) {
		String prefThemeId = getPreferenceThemeId();

		if ("org.eclipse.e4.ui.css.theme.e4_classic6.0,6.1,6.2,6.3".equals(prefThemeId)) { //$NON-NLS-1$
			prefThemeId = "org.eclipse.e4.ui.css.theme.e4_classic"; //$NON-NLS-1$
		}

		boolean flag = true;
		if (prefThemeId != null) {
			for (ITheme t : getThemes()) {
				if (prefThemeId.equals(t.getId())) {
					setTheme(t, false);
					flag = false;
					break;
