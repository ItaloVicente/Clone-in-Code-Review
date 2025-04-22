		BundleContext bundleContext = Activator.getDefault().getBundleContext();
		if (bundleContext == null) {
			if (logger != null) {
				logger.error(NLS.bind(ServiceMessages.NO_BUNDLE_CONTEXT, topic));
			}
			return false;
		}
