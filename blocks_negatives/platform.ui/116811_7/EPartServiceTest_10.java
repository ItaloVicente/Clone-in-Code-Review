	@Test
	public void testGetInputParts_Bug334559_01() {
		MWindow window = ems.createModelElement(MWindow.class);
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPerspectiveStack perspectiveStack = ems.createModelElement(MPerspectiveStack.class);
		window.getChildren().add(perspectiveStack);
		window.setSelectedElement(perspectiveStack);

		MPerspective perspective = ems.createModelElement(MPerspective.class);
		perspectiveStack.getChildren().add(perspective);
		perspectiveStack.setSelectedElement(perspective);

		MInputPart partA = ems.createModelElement(MInputPart.class);
		window.getChildren().add(partA);
		window.setSelectedElement(partA);

		MWindow detachedWindow = ems.createModelElement(MWindow.class);
		perspective.getWindows().add(detachedWindow);

		MInputPart partB = ems.createModelElement(MInputPart.class);
		detachedWindow.getChildren().add(partB);
		detachedWindow.setSelectedElement(partB);

		MInputPart partC = ems.createModelElement(MInputPart.class);
		detachedWindow.getChildren().add(partC);

		initialize();
		getEngine().createGui(window);

		EPartService partService = window.getContext().get(EPartService.class);
		assertEquals(3, parts.size());
		assertTrue(parts.contains(partA));
		assertTrue(parts.contains(partB));
		assertTrue(parts.contains(partC));
	}

	@Test
	public void testGetInputParts_Bug334559_02() {
		MWindow window = ems.createModelElement(MWindow.class);
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPerspectiveStack perspectiveStack = ems.createModelElement(MPerspectiveStack.class);
		window.getChildren().add(perspectiveStack);
		window.setSelectedElement(perspectiveStack);

		MPerspective perspective = ems.createModelElement(MPerspective.class);
		perspectiveStack.getChildren().add(perspective);
		perspectiveStack.setSelectedElement(perspective);

		MInputPart partA = ems.createModelElement(MInputPart.class);
		window.getChildren().add(partA);

		MWindow detachedWindow = ems.createModelElement(MWindow.class);
		perspective.getWindows().add(detachedWindow);

		MInputPart partB = ems.createModelElement(MInputPart.class);
		detachedWindow.getChildren().add(partB);
		detachedWindow.setSelectedElement(partB);

		MInputPart partC = ems.createModelElement(MInputPart.class);
		detachedWindow.getChildren().add(partC);

		initialize();
		getEngine().createGui(window);

		EPartService partService = window.getContext().get(EPartService.class);
		assertEquals(3, parts.size());
		assertTrue(parts.contains(partA));
		assertTrue(parts.contains(partB));
		assertTrue(parts.contains(partC));
	}

	@Test
	public void testGetInputParts() {

		MWindow window = ems.createModelElement(MWindow.class);
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPart part = ems.createModelElement(MPart.class);
		window.getChildren().add(part);

		MInputPart inputPart = ems.createModelElement(MInputPart.class);
		inputPart.setInputURI(uri1);
		window.getChildren().add(inputPart);

		part = ems.createModelElement(MPart.class);
		window.getChildren().add(part);

		inputPart = ems.createModelElement(MInputPart.class);
		inputPart.setInputURI(uri2);
		window.getChildren().add(inputPart);

		inputPart = ems.createModelElement(MInputPart.class);
		inputPart.setInputURI(uri1);
		window.getChildren().add(inputPart);

		part = ems.createModelElement(MPart.class);
		window.getChildren().add(part);

		initialize();
		getEngine().createGui(window);

		EPartService partService = window.getContext().get(EPartService.class);
		assertEquals(6, partService.getParts().size());
		assertEquals(2, partService.getInputParts(uri1).size());
		assertEquals(1, partService.getInputParts(uri2).size());
		assertEquals(0, partService.getInputParts("totally unknown").size());
		try {
			partService.getInputParts(null);
			fail("Passing null should throw an AssertionFailedException");
		} catch (AssertionFailedException e) {
		}
	}
