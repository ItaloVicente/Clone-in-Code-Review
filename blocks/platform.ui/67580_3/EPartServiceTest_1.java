	@Test
	public void testPartActivationTimeData_Bug461063() {
		createApplication("activePart", "nonActivePart");

		MWindow window = application.getChildren().get(0);
		MPartStack partStack = (MPartStack) window.getChildren().get(0);
		MPart activePart = (MPart) partStack.getChildren().get(0);
		MPart nonActivePart = (MPart) partStack.getChildren().get(1);
		partStack.setSelectedElement(activePart);

		getEngine().createGui(window);

		EPartService partService = window.getContext().get(EPartService.class);
		assertTrue(activePart.getTransientData().containsKey(PartServiceImpl.PART_ACTIVATION_TIME));
		assertFalse(nonActivePart.getTransientData().containsKey(PartServiceImpl.PART_ACTIVATION_TIME));

		assertTrue(Long.class.isInstance(activePart.getTransientData().get(PartServiceImpl.PART_ACTIVATION_TIME)));

		partService.activate(nonActivePart);

		assertTrue(activePart.getTransientData().containsKey(PartServiceImpl.PART_ACTIVATION_TIME));
		assertTrue(nonActivePart.getTransientData().containsKey(PartServiceImpl.PART_ACTIVATION_TIME));
	}

