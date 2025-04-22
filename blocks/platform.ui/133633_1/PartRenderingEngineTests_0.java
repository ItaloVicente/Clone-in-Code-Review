	@Test
	public void cleanUpAdddOnShouldRemoveEmptyStacks() {
		MApplication application = ems.createModelElement(MApplication.class);

		MWindow window = ems.createModelElement(MWindow.class);
		application.getChildren().add(window);
		application.setSelectedElement(window);

		MPartSashContainer sashContainer = ems.createModelElement(MPartSashContainer.class);
		window.getChildren().add(sashContainer);
		window.setSelectedElement(sashContainer);

		MPartStack partStackA = ems.createModelElement(MPartStack.class);
		sashContainer.getChildren().add(partStackA);
		sashContainer.setSelectedElement(partStackA);

		MPart partA = ems.createModelElement(MPart.class);
		partStackA.getChildren().add(partA);
		partStackA.setSelectedElement(partA);

		MPart partB = ems.createModelElement(MPart.class);
		partStackA.getChildren().add(partB);
		partStackA.setSelectedElement(partB);

		MPartStack partStackB = ems.createModelElement(MPartStack.class);
		sashContainer.getChildren().add(partStackB);
		sashContainer.setSelectedElement(partStackB);

		MPart partC = ems.createModelElement(MPart.class);
		partStackB.getChildren().add(partC);
		partStackB.setSelectedElement(partC);

		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		ContextInjectionFactory.make(CleanupAddon.class, appContext);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		EPartService partService = window.getContext().get(EPartService.class);
		partService.hidePart(partB);
		spinEventLoop();

		partService.hidePart(partA, true);
		spinEventLoop();

		partService.hidePart(partC, true);
		spinEventLoop();

		assertNull(partStackA);
	}

