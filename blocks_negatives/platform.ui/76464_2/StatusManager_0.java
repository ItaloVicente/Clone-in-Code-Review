		if(statusHandler == null && StatusHandlerRegistry.getDefault()
					.getDefaultHandlerDescriptor() != null){
			try {
				statusHandler = StatusHandlerRegistry.getDefault()
						.getDefaultHandlerDescriptor().getStatusHandler();
			} catch (CoreException ex) {
				logError("Errors during the default handler creating", ex); //$NON-NLS-1$
			}
