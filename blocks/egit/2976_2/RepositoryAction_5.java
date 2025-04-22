	private IServiceLocator getServiceLocator() {
		if (serviceLocator == null)
			serviceLocator = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		return serviceLocator;
	}

