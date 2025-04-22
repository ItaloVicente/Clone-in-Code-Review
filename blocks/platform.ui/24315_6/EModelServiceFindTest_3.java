		URI uri = URI.createPlatformPluginURI("org.eclipse.e4.ui.tests/xmi/SearchModelElement.e4xmi", true);
		ResourceSet set = new ResourceSetImpl();
		Resource resource = set.getResource(uri, true);

		assertNotNull(resource);
		assertEquals(E4XMIResource.class, resource.getClass());
		assertEquals(1, resource.getContents().size());
		MApplication app = (MApplication) resource.getContents().get(0);
