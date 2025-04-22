	public void ensureCleanUpAddonCleansUp() {
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
