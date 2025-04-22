			Bundle bundle = WorkbenchSWTActivator.getDefault().getBundle();
			BundleContext context = bundle.getBundleContext();
			ServiceReference ref = context
					.getServiceReference(IThemeManager.class.getName());
			IThemeManager mgr = (IThemeManager) context.getService(ref);
			final IThemeEngine engine = mgr.getEngineForDisplay(display);

			IContributionFactory contribution = (IContributionFactory) appContext
					.get(IContributionFactory.class.getName());
			IEclipseContext cssContext = EclipseContextFactory.create();
			cssContext.set(IContributionFactory.class.getName(), contribution);
			display.setData("org.eclipse.e4.ui.css.context", cssContext); //$NON-NLS-1$

