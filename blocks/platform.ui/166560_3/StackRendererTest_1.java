	@Test
	public void testBug564561_ToolbarVisible_initial() {
		MPart part1 = ems.createModelElement(MPart.class);
		MPart part2 = ems.createModelElement(MPart.class);

		partStack.getChildren().add(part1);
		partStack.getChildren().add(part2);
		partStack.setSelectedElement(part1);

		MToolBar toolbar1 = ems.createModelElement(MToolBar.class);
		toolbar1.setVisible(false);
		part1.setToolbar(toolbar1);

		MToolBar toolbar2 = ems.createModelElement(MToolBar.class);
		toolbar2.setVisible(true);
		part2.setToolbar(toolbar2);

		contextRule.createAndRunWorkbench(window);

		assertTrue(toolbar1.isVisible());
		assertFalse(toolbar2.isVisible());

		partStack.setSelectedElement(part2);

		assertFalse(toolbar1.isVisible());
		assertTrue(toolbar2.isVisible());
	}

	@Test
	public void testBug564561_ToolbarVisible_added1() {
		MPart part1 = ems.createModelElement(MPart.class);

		partStack.getChildren().add(part1);
		partStack.setSelectedElement(part1);

		contextRule.createAndRunWorkbench(window);

		MPart part2 = ems.createModelElement(MPart.class);
		MToolBar toolbar2 = ems.createModelElement(MToolBar.class);
		toolbar2.setVisible(true);
		part2.setToolbar(toolbar2);

		partStack.getChildren().add(part2);

		assertFalse(toolbar2.isVisible());
	}

	@Test
	public void testBug564561_ToolbarVisible_added2() {
		MPart part1 = ems.createModelElement(MPart.class);
		MPart part2 = ems.createModelElement(MPart.class);

		partStack.getChildren().add(part1);
		partStack.getChildren().add(part2);
		partStack.setSelectedElement(part1);

		contextRule.createAndRunWorkbench(window);

		MToolBar toolbar1 = ems.createModelElement(MToolBar.class);
		toolbar1.setVisible(false);
		part1.setToolbar(toolbar1);

		MToolBar toolbar2 = ems.createModelElement(MToolBar.class);
		toolbar2.setVisible(true);
		part2.setToolbar(toolbar2);

		assertTrue(toolbar1.isVisible());
		assertFalse(toolbar2.isVisible());
	}



