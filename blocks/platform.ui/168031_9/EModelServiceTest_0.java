	@Test
	public void testMoveWithoutIndexWithOneOtherElementInTheSameContainer() {
		var window = ems.createModelElement(MWindow.class);
		var part1 = ems.createModelElement(MPart.class);
		var part2 = ems.createModelElement(MPart.class);
		window.getChildren().add(part1);
		window.getChildren().add(part2);
		var modelService = applicationContext.get(EModelService.class);
		modelService.move(part1, window);
		assertSame(part1, window.getChildren().get(1));
	}

	@Test
	public void testMoveWithIndexWithTwoOtherElementInTheSameContainer() {
		var window = ems.createModelElement(MWindow.class);
		var part1 = ems.createModelElement(MPart.class);
		var part2 = ems.createModelElement(MPart.class);
		var part3 = ems.createModelElement(MPart.class);
		window.getChildren().add(part1);
		window.getChildren().add(part2);
		window.getChildren().add(part3);
		window.setSelectedElement(part2);
		var modelService = applicationContext.get(EModelService.class);
		modelService.move(part1, window, 1);
		assertSame(part1, window.getChildren().get(1));
		assertSame(part2, window.getSelectedElement());
	}

	@Test
	public void testMoveWithIndexWithTwoOtherElementInTheSameContainerKeepSelection() {
		var window = ems.createModelElement(MWindow.class);
		var part1 = ems.createModelElement(MPart.class);
		var part2 = ems.createModelElement(MPart.class);
		var part3 = ems.createModelElement(MPart.class);
		window.getChildren().add(part1);
		window.getChildren().add(part2);
		window.getChildren().add(part3);
		window.setSelectedElement(part1);
		var modelService = applicationContext.get(EModelService.class);
		modelService.move(part1, window, 1);
		assertSame(part1, window.getChildren().get(1));
		assertSame(part1, window.getSelectedElement());
	}

