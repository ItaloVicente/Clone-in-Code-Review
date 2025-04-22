	public void testCountRenderableChildren_WithWindows() {
		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPerspectiveStack perspectiveStack = AdvancedFactoryImpl.eINSTANCE.createPerspectiveStack();
		window.getChildren().add(perspectiveStack);
		window.setSelectedElement(perspectiveStack);

		MPerspective perspective = AdvancedFactoryImpl.eINSTANCE.createPerspective();
		perspectiveStack.getChildren().add(perspective);
		perspectiveStack.setSelectedElement(perspective);

		MPartStack partStack = BasicFactoryImpl.eINSTANCE.createPartStack();
		perspective.getChildren().add(partStack);
		perspective.setSelectedElement(partStack);

		MWindow perspectiveWindow = BasicFactoryImpl.eINSTANCE.createWindow();
		perspective.getWindows().add(perspectiveWindow);

		getEngine().createGui(window);

		EModelService modelService = window.getContext().get(EModelService.class);
		assertEquals(2, modelService.countRenderableChildren(perspective));
	}

