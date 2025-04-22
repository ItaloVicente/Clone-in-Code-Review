		ensureEventAdminStarted();
		BundleContext bundleContext = Activator.getDefault().getBundle()
				.getBundleContext();
		IEclipseContext localContext = EclipseContextFactory
				.getServiceContext(bundleContext);
		helper = ContextInjectionFactory.make(EventAdminHelper.class,
				localContext);
