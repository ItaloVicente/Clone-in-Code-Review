
		URI initialWorkbenchDefinitionInstance;

		if (URIHelper.isPlatformURI(appModelPath)) {
			initialWorkbenchDefinitionInstance = URI.createURI(appModelPath,
					true);
		} else {
			initialWorkbenchDefinitionInstance = URI.createPlatformPluginURI(
					appModelPath, true);
		}
