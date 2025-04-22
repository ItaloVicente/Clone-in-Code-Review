	@Test
	public void testBug572598_SharedPartAndToolbarNotDisposed() {
		MPart part = ems.createModelElement(MPart.class);
		window.getSharedElements().add(part);

		MToolBar toolbar = ems.createModelElement(MToolBar.class);
		part.setToolbar(toolbar);

		MPlaceholder ph1 = ems.createModelElement(MPlaceholder.class);
		ph1.setRef(part);
		partStack.getChildren().add(ph1);
		partStack.setSelectedElement(ph1);

		MPartStack partStack2 = ems.createModelElement(MPartStack.class);
		window.getChildren().add(partStack2);
		window.setSelectedElement(partStack2);

		MPlaceholder ph2 = ems.createModelElement(MPlaceholder.class);
		ph2.setRef(part);
		partStack2.getChildren().add(ph2);
		partStack2.setSelectedElement(ph2);

		contextRule.createAndRunWorkbench(window);

		assertNotNull(part.getWidget());
		assertFalse(((Widget) part.getWidget()).isDisposed());
		assertNotNull(toolbar.getWidget());
		assertFalse(((Widget) toolbar.getWidget()).isDisposed());

		partStack2.setToBeRendered(false);

		assertNotNull(part.getWidget());
		assertFalse(((Widget) part.getWidget()).isDisposed());
		assertNotNull(toolbar.getWidget());
		assertFalse(((Widget) toolbar.getWidget()).isDisposed());
	}

