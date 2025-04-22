	@SuppressWarnings("unchecked")
	private void setupSSH(final BundleContext context) {
		final ServiceReference ssh;

		ssh = context.getServiceReference(IJSchService.class.getName());
		if (ssh != null) {
			SshSessionFactory.setInstance(new EclipseSshSessionFactory(
					(IJSchService) context.getService(ssh)));
		}
	}

	private void setupProxy(final BundleContext context) {
		final ServiceReference proxy;

		proxy = context.getServiceReference(IProxyService.class.getName());
		if (proxy != null) {
			ProxySelector.setDefault(new EclipseProxySelector(
					(IProxyService) context.getService(proxy)));
			Authenticator.setDefault(new EclipseAuthenticator(
					(IProxyService) context.getService(proxy)));
		}
	}

