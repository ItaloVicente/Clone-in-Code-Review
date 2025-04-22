					if(!backwardsCompatURIsLogged) {
						CSSActivator.getDefault().log(LogService.LOG_ERROR,
						backwardsCompatURIsLogged = true;
					}
					rendURL = rendURL.replace("platform:/plugin/", "bundleclass://"); //$NON-NLS-1$ //$NON-NLS-2$
				}
