	public void testActivatePartInInactivePerspective() {
		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPart partA = BasicFactoryImpl.eINSTANCE.createPart();
		window.getSharedElements().add(partA);

		MPart partB = BasicFactoryImpl.eINSTANCE.createPart();
		window.getSharedElements().add(partB);

		MPerspectiveStack perspectiveStack = AdvancedFactoryImpl.eINSTANCE.createPerspectiveStack();
		window.getChildren().add(perspectiveStack);
		window.setSelectedElement(perspectiveStack);

		MPerspective perspectiveA = AdvancedFactoryImpl.eINSTANCE.createPerspective();
		perspectiveStack.getChildren().add(perspectiveA);
		perspectiveStack.setSelectedElement(perspectiveA);

		MPerspective perspectiveB = AdvancedFactoryImpl.eINSTANCE.createPerspective();
		perspectiveStack.getChildren().add(perspectiveB);

		perspectiveA.getChildren().add(partA);
		perspectiveB.getChildren().add(partB);

		initialize();
		getEngine().createGui(window);

		EPartService partService = window.getContext().get(EPartService.class);
		partService.activate(partB);

		assertEquals(partB, partService.getActivePart());
	}

	public void testActivatePartInInactivePerspectiveWithPartStacks() {
		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPart partA = BasicFactoryImpl.eINSTANCE.createPart();
		window.getSharedElements().add(partA);

		MPart partB = BasicFactoryImpl.eINSTANCE.createPart();
		window.getSharedElements().add(partB);

		MPerspectiveStack perspectiveStack = AdvancedFactoryImpl.eINSTANCE.createPerspectiveStack();
		window.getChildren().add(perspectiveStack);
		window.setSelectedElement(perspectiveStack);

		MPerspective perspectiveA = AdvancedFactoryImpl.eINSTANCE.createPerspective();
		perspectiveStack.getChildren().add(perspectiveA);
		perspectiveStack.setSelectedElement(perspectiveA);

		MPartStack partStackA = BasicFactoryImpl.eINSTANCE.createPartStack();
		perspectiveA.getChildren().add(partStackA);
		perspectiveA.setSelectedElement(partStackA);

		MPerspective perspectiveB = AdvancedFactoryImpl.eINSTANCE.createPerspective();
		perspectiveStack.getChildren().add(perspectiveB);

		MPartStack partStackB = BasicFactoryImpl.eINSTANCE.createPartStack();
		perspectiveB.getChildren().add(partStackB);
		perspectiveB.setSelectedElement(partStackB);

		partStackA.getChildren().add(partA);
		partStackB.getChildren().add(partB);

		initialize();
		getEngine().createGui(window);

		EPartService partService = window.getContext().get(EPartService.class);
		partService.activate(partB);

		assertEquals(partB, partService.getActivePart());
	}

