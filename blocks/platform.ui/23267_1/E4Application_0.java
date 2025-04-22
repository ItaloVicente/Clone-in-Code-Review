	private void setCSSContextVariables(IApplicationContext applicationContext,
			IEclipseContext context) {
		boolean highContrastMode = getApplicationDisplay().getHighContrast();

		String cssURI = highContrastMode ? HIGH_CONTRAST_CSS_STYLESHEET_URI
				: getArgValue(IWorkbench.CSS_URI_ARG, applicationContext, false);

		if (cssURI != null) {
			context.set(IWorkbench.CSS_URI_ARG, cssURI);
		}

		String themeId = highContrastMode ? null : getArgValue(
				E4Application.THEME_ID, applicationContext, false);

		if (themeId == null && cssURI == null) {
			themeId = DEFAULT_THEME_ID;
		}

		context.set(E4Application.THEME_ID, themeId);

		if (cssURI != null && !cssURI.startsWith("platform:/plugin/")) {
			System.err
					.println("Warning. Use the \"platform:/plugin/Bundle-SymbolicName/path/filename.extension\" URI for the  parameter:   "
							+ IWorkbench.CSS_URI_ARG); //$NON-NLS-1$
			context.set(E4Application.THEME_ID, cssURI);
		}

		String cssResourcesURI = getArgValue(IWorkbench.CSS_RESOURCE_URI_ARG,
				applicationContext, false);
		context.set(IWorkbench.CSS_RESOURCE_URI_ARG, cssResourcesURI);
	}

