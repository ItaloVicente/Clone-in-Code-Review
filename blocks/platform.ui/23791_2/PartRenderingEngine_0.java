
	private static IThemeEngine createThemeEngine(Display display,
			IEclipseContext appContext) {
		IContributionFactory contribution = (IContributionFactory) appContext
				.get(IContributionFactory.class.getName());
		IEclipseContext cssContext = EclipseContextFactory.create();
		cssContext.set(IContributionFactory.class.getName(), contribution);
		display.setData("org.eclipse.e4.ui.css.context", cssContext); //$NON-NLS-1$

		IThemeManager mgr = appContext.get(IThemeManager.class);
		IThemeEngine themeEngine = mgr.getEngineForDisplay(display);

		appContext.set(IThemeEngine.class.getName(), themeEngine);
		return themeEngine;
	}
