		BundleContext context = IDEWorkbenchPlugin.getDefault().getBundle().getBundleContext();
		if (context == null)
			return;
		ServiceReference<PlatformAdmin> reference = context.getServiceReference(PlatformAdmin.class);
		if (reference == null)
			return;
		PlatformAdmin admin = context.getService(reference);
		try {
