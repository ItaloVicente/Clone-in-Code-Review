
		if (hs == null) {
			Activator.log(LogService.LOG_ERROR, "IEclipseContext is " + eclipseContext); //$NON-NLS-1$
			Activator.log(LogService.LOG_ERROR, "EHandlerService is null", //$NON-NLS-1$
					new IllegalStateException("EHandlerService must not be null")); //$NON-NLS-1$
		}

