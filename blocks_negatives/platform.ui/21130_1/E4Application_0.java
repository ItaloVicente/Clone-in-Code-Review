		String themeId = getArgValue(E4Application.THEME_ID,
				applicationContext, false);
		if (themeId == null && cssURI == null) {
			themeId = DEFAULT_THEME_ID;
		}
		appContext.set(E4Application.THEME_ID, themeId);

