	@Test
	public void ensureCleanUpAddonCleansUp() {
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

		MPartStack partStackForPartBPartC = ems.createModelElement(MPartStack.class);
		sashContainer.getChildren().add(partStackForPartBPartC);
		sashContainer.setSelectedElement(partStackForPartBPartC);

		MPart partB = ems.createModelElement(MPart.class);
		partB.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
		partStackForPartBPartC.getChildren().add(partB);
		partStackForPartBPartC.setSelectedElement(partB);

		MPart partC = ems.createModelElement(MPart.class);
		partB.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
		partStackForPartBPartC.getChildren().add(partC);
		partStackForPartBPartC.setSelectedElement(partC);

		MPartStack partStackForEditor = ems.createModelElement(MPartStack.class);
		partStackForEditor.getTags().add(IPresentationEngine.NO_AUTO_COLLAPSE);
		sashContainer.getChildren().add(partStackForEditor);

		MPart editor = ems.createModelElement(MPart.class);
		editor.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
		partStackForEditor.getChildren().add(editor);
		partStackForEditor.setSelectedElement(editor);

		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		ContextInjectionFactory.make(CleanupAddon.class, appContext);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		EPartService partService = window.getContext().get(EPartService.class);
		partService.hidePart(partA);
		spinEventLoop();

		assertTrue(" PartStack with children should be rendered", partStackForPartBPartC.isToBeRendered());
		partService.hidePart(partB);
		partService.hidePart(partC);
		spinEventLoop();
		assertTrue("CleanupAddon should ensure that partStack is not rendered anymore, as all childs have been removed",
				!partStackForPartBPartC.isToBeRendered());
		assertTrue("Part stack should be removed", !partStackForPartBPartC.isToBeRendered());
		partService.hidePart(editor, true);
		spinEventLoop();
		assertTrue("PartStack with IPresentationEngine.NO_AUTO_COLLAPSE should not be closed if children are removed",
				partStackForEditor.isToBeRendered());

	}

