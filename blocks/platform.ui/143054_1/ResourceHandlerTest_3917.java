	@Test
	public void testDynamicElementsDoNotGetPersisted() throws IOException {
		URI uri = URI.createPlatformPluginURI("org.eclipse.e4.ui.tests/xmi/modelprocessor/base.e4xmi", true);

		ResourceHandler handler = createHandler(uri);
		Resource resource = handler.loadMostRecentModel();
		MApplication application = (MApplication) resource.getContents().get(0);
		assertNotNull(application);
		assertEquals(2, application.getChildren().size());

		MWindow dynamicWindow = MBasicFactory.INSTANCE.createWindow();
		dynamicWindow.setLabel("Dynamically generated window");
		application.getChildren().add(dynamicWindow);

		Path changedOutput = Files.createTempFile(null, null);
		changedOutput.toFile().deleteOnExit();
		URI changedUri = URI.createFileURI(changedOutput.toString());
		resource.setURI(changedUri);
		handler.save();

		ResourceHandler verifyHandler = createHandler(changedUri);
		Resource verifyResource = verifyHandler.loadMostRecentModel();
		MApplication changedApplication = (MApplication) verifyResource.getContents().get(0);
		assertEquals(3, changedApplication.getChildren().size());

		dynamicWindow.getPersistedState().put(IWorkbench.PERSIST_STATE, "false");

		Path unchangedOutput = Files.createTempFile(null, null);
		unchangedOutput.toFile().deleteOnExit();
		URI unchangedUri = URI.createFileURI(unchangedOutput.toString());
		resource.setURI(unchangedUri);
		handler.save();

		verifyHandler = createHandler(unchangedUri);
		verifyResource = verifyHandler.loadMostRecentModel();
		MApplication unchangedApplication = (MApplication) verifyResource.getContents().get(0);
		assertEquals(2, unchangedApplication.getChildren().size());
	}
