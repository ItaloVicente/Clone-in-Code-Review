	@Test
	public void testBug564299() {
		MWindow window = ems.createModelElement(MWindow.class);

		MPart part = ems.createModelElement(MPart.class);
		window.getSharedElements().add(part);
		part.setContributionURI(
				"platform:/plugin/org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.SampleView");
		MToolBar toolbar = ems.createModelElement(MToolBar.class);
		part.setToolbar(toolbar);

		MPartSashContainer partSash = ems.createModelElement(MPartSashContainer.class);
		window.getChildren().add(partSash);

		MPlaceholder placeholder1 = ems.createModelElement(MPlaceholder.class);
		placeholder1.setRef(part);
		partSash.getChildren().add(placeholder1);

		MPerspectiveStack perspectiveStack = ems.createModelElement(MPerspectiveStack.class);
		partSash.getChildren().add(perspectiveStack);

		MPerspective perspective = ems.createModelElement(MPerspective.class);
		perspectiveStack.getChildren().add(perspective);
		perspectiveStack.setSelectedElement(perspective);

		MPlaceholder placeholder2 = ems.createModelElement(MPlaceholder.class);
		placeholder2.setRef(part);
		perspective.getChildren().add(placeholder2);

		application.getChildren().add(window);
		contextRule.createAndRunWorkbench(window);

		assertTrue(placeholder1.isToBeRendered());
		assertTrue(placeholder2.isToBeRendered());

		MDirectToolItem item = ems.createModelElement(MDirectToolItem.class);
		toolbar.getChildren().add(item);

		assertTrue(placeholder1.isToBeRendered());
		assertTrue(placeholder2.isToBeRendered());
	}

