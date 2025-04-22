	public static void disableProxy() {
		BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference serviceReference = context.getServiceReference(IProxyService.class.getName());
		IProxyService proxyService = (IProxyService) context.getService(serviceReference);
		proxyService.setSystemProxiesEnabled(false);
		proxyService.setProxiesEnabled(false);
	}

