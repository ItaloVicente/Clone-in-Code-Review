	private URI determineApplicationModelURI(IApplicationContext appContext) {
		String appModelPath = getArgValue(IWorkbench.XMI_URI_ARG, appContext, false);
		if (appModelPath == null || appModelPath.length() == 0) {
			Bundle brandingBundle = appContext.getBrandingBundle();
			if (brandingBundle != null)
				appModelPath = brandingBundle.getSymbolicName() + "/" + E4Application.APPLICATION_MODEL_PATH_DEFAULT;
			else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(
						new Exception(), // log a stack trace for debugging
						"applicationXMI parameter not set and no branding plugin defined. "); //$NON-NLS-1$
			}
		}
		
		URI applicationModelURI = null;

		if (URIHelper.isPlatformURI(appModelPath)) {
			applicationModelURI = URI.createURI(appModelPath, true);
		} else {
			applicationModelURI = URI.createPlatformPluginURI(appModelPath, true);
		}
		return applicationModelURI;
	
	}

