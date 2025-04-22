		String cssURI = getArgValue(IWorkbench.CSS_URI_ARG, applicationContext,
				false);
		if (cssURI != null) {
			appContext.set(IWorkbench.CSS_URI_ARG, cssURI);
		}

		String themeId = getArgValue(E4Application.THEME_ID,
				applicationContext, false);
		if (themeId == null && cssURI == null) {
			themeId = DEFAULT_THEME_ID;
		}
		appContext.set(E4Application.THEME_ID, themeId);

		if (cssURI != null && !cssURI.startsWith("platform:/plugin/")) {
			System.err
					.println("Warning. "
							+ "Use the \"platform:/plugin/Bundle-SymbolicName/path/filename.extension\" "
			appContext.set(E4Application.THEME_ID, cssURI);
		}
