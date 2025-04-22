
		StatusHandlerDescriptor defaultHandlerDescriptor = StatusHandlerRegistry.getDefault()
				.getDefaultHandlerDescriptor();

		synchronized (this) {
			if (statusHandler == null) {
				if (defaultHandlerDescriptor != null) {
					try {
						statusHandler = defaultHandlerDescriptor.getStatusHandler();
					} catch (CoreException ex) {
						logError("Errors during the default handler creating", ex); //$NON-NLS-1$
					}
				}
				if (statusHandler == null) {
					statusHandler = new WorkbenchErrorHandlerProxy();
				}
			}
