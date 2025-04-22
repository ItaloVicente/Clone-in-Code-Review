		BundleContext context = IDEWorkbenchPlugin.getDefault().getBundle().getBundleContext();
		if (context == null)
			return;

		ServiceReference<IProfileRegistry> reference = context.getServiceReference(IProfileRegistry.class);
		if (reference == null)
			return;
		try {
			IProfileRegistry registry = context.getService(reference);
			if (registry == null)
				return;
