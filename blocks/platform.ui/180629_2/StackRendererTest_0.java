	@Test
	public void testBug573518_SharedPartToolbarShown1() {
		MPart part1 = ems.createModelElement(MPart.class);
		window.getSharedElements().add(part1);

		MPart part2 = ems.createModelElement(MPart.class);
		window.getSharedElements().add(part2);

		MToolBar toolbar = ems.createModelElement(MToolBar.class);
		part2.setToolbar(toolbar);

		MPlaceholder ph1 = ems.createModelElement(MPlaceholder.class);
		ph1.setRef(part1);
		partStack.getChildren().add(ph1);
		partStack.setSelectedElement(ph1);

		MPlaceholder ph2 = ems.createModelElement(MPlaceholder.class);
		ph2.setRef(part2);
		partStack.getChildren().add(ph2);

		contextRule.createAndRunWorkbench(window);

		assertTrue(toolbar.isVisible());
		assertNull(toolbar.getWidget());

		partStack.setSelectedElement(ph2);

		assertTrue(toolbar.isVisible());
		assertNotNull(toolbar.getWidget());
	}

	@Test
	public void testBug573518_SharedPartToolbarShown2() {
		MPart part1 = ems.createModelElement(MPart.class);
		window.getSharedElements().add(part1);

		MPart part2 = ems.createModelElement(MPart.class);
		window.getSharedElements().add(part2);

		MToolBar toolbar = ems.createModelElement(MToolBar.class);
		part2.setToolbar(toolbar);

		MPlaceholder ph1 = ems.createModelElement(MPlaceholder.class);
		ph1.setRef(part1);
		partStack.getChildren().add(ph1);
		partStack.setSelectedElement(ph1);

		MPlaceholder ph2 = ems.createModelElement(MPlaceholder.class);
		ph2.setRef(part2);
		part2.setCurSharedRef(ph2);
		partStack.getChildren().add(ph2);

		contextRule.createAndRunWorkbench(window);

		assertFalse(toolbar.isVisible());
		assertNull(toolbar.getWidget());

		partStack.setSelectedElement(ph2);

		assertTrue(toolbar.isVisible());
		assertNotNull(toolbar.getWidget());
	}

