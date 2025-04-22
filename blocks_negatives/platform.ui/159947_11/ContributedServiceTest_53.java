		IServiceLocatorCreator lc = parent
				.getService(IServiceLocatorCreator.class);
		IServiceLocator locator = lc.createServiceLocator(parent,
				new AbstractServiceFactory() {
					@Override
					public Object create(Class serviceInterface,
							IServiceLocator parentLocator,
							IServiceLocator locator) {
						if (IWorkbenchLocationService.class
								.equals(serviceInterface)) {
							IWorkbenchLocationService wls = parentLocator
									.getService(IWorkbenchLocationService.class);
							return new WorkbenchLocationService(
									IServiceScopes.DIALOG_SCOPE, wls
											.getWorkbench(), null, null, null,
									null, wls.getServiceLevel()+1);
						}
						return null;
					}
				}, () -> {
				});
		IWorkbenchLocationService wls = locator
				.getService(IWorkbenchLocationService.class);
