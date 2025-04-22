	@Test
	public void testCreatePart_WithVariables() {
		createApplication(1, new String[1][0]);
		MWindow window = application.getChildren().get(0);
		MPartDescriptor partDescriptor = org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicFactoryImpl.eINSTANCE
				.createPartDescriptor();
		partDescriptor.setElementId("partId");
		partDescriptor.getVariables().add("testVariable");
		partDescriptor.getProperties().put("testVariable", "testValue");
		application.getDescriptors().add(partDescriptor);

		getEngine().createGui(window);

		EPartService partService = (EPartService) window.getContext().get(EPartService.class.getName());

		MPart part = partService.createPart("partId");
		assertNotNull(part);
		assertEquals(1, part.getVariables().size());
		assertEquals("testVariable", part.getVariables().get(0));
		assertEquals(1, part.getProperties().size());
		assertTrue(part.getProperties().containsKey("testVariable"));
		assertEquals("testValue", part.getProperties().get("testVariable"));
	}

