		BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference<LogReaderService> reference = context.getServiceReference(LogReaderService.class);
		if (reference != null) {
			logReaderService = context.getService(reference);
		}
		if (logReaderService != null) {
			logReaderService.addLogListener(this);
		}

