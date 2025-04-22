
		URI uri = URI.createPlatformPluginURI("org.eclipse.e4.ui.tests/xmi/modelprocessor/base.e4xmi", true);
		ResourceHandler handler = createHandler(uri);
		Resource resource = handler.loadMostRecentModel();
		MApplication application = (MApplication) resource.getContents().get(0);
		assertNotNull(application);

