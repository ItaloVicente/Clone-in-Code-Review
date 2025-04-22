	protected IWorkbenchWindow getWindow() {
		if (serviceLocator == null)
			return null;

		IWorkbenchLocationService wls = (IWorkbenchLocationService) serviceLocator
				.getService(IWorkbenchLocationService.class);

		if (window == null) {
			window = wls.getWorkbenchWindow();
		}
		if (window == null) {
			IWorkbench wb = wls.getWorkbench();
			if (wb != null) {
				window = wb.getActiveWorkbenchWindow();
			}
		}
		return window;
	}

