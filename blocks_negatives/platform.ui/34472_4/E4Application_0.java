		Location instanceLocation = WorkbenchSWTActivator.getDefault()
				.getInstanceLocation();

		String appModelPath = getArgValue(IWorkbench.XMI_URI_ARG, appContext,
				false);
		if (appModelPath == null || appModelPath.length() == 0) {
			Bundle brandingBundle = appContext.getBrandingBundle();
			if (brandingBundle != null)
				appModelPath = brandingBundle.getSymbolicName() + "/"
						+ E4Application.APPLICATION_MODEL_PATH_DEFAULT;
			else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(
						new Exception(), // log a stack trace for debugging
			}
		}

		URI initialWorkbenchDefinitionInstance;

		if (URIHelper.isPlatformURI(appModelPath)) {
			initialWorkbenchDefinitionInstance = URI.createURI(appModelPath,
					true);
		} else {
			initialWorkbenchDefinitionInstance = URI.createPlatformPluginURI(
					appModelPath, true);
		}

		eclipseContext.set(E4Workbench.INITIAL_WORKBENCH_MODEL_URI,
				initialWorkbenchDefinitionInstance);
