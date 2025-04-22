
		try {
			BundleContext context = WorkbenchSWTActivator.getDefault().getContext();
			Collection<ServiceReference<UrlSchemeHandler>> serviceReferences = context
					.getServiceReferences(UrlSchemeHandler.class, null);
			for (ServiceReference<UrlSchemeHandler> ref : serviceReferences) {
				UrlSchemeHandler service = context.getService(ref);
				schemeHandler.put(service.getScheme(), service);
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
