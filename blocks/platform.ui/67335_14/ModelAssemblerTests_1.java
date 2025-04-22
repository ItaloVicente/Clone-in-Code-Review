	@Test
	public void testModelProcessingOrder() throws Exception {
		MTrimmedWindow trimmedWindow = MBasicFactory.INSTANCE.createTrimmedWindow();
		trimmedWindow.setElementId("testModelProcessingOrder-trimmedWindow");
		application.getChildren().add(trimmedWindow);
		MPart part = MBasicFactory.INSTANCE.createPart();
		part.setElementId("testModelProcessingOrder-part");
		trimmedWindow.getChildren().add(part);
		MArea area = MAdvancedFactory.INSTANCE.createArea();
		area.setElementId("testModelProcessingOrder-area");
		trimmedWindow.getChildren().add(area);

		IContributor contributor = ContributorFactorySimple.createContributor(BUNDLE_SYMBOLIC_NAME);
		IExtensionRegistry registry = createTestExtensionRegistry();
		assertEquals(0, registry.getConfigurationElementsFor(EXTENSION_POINT_ID).length);
		String dataFilePath = "org.eclipse.e4.ui.tests/data/ModelAssembler/modelProcessingOrder.xml";
		registry.addContribution(getContentsAsInputStream(dataFilePath), contributor, false, null, null, null);

		assembler.processModel(true);

		verifyZeroInteractions(logger);
	}

