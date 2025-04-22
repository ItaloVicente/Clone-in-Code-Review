	@Test
	public void moveWithInTheSameContainer() {
		var window = ems.createModelElement(MWindow.class);
		var part1 = ems.createModelElement(MPart.class);
		var part2 = ems.createModelElement(MPart.class);
		var part3 = ems.createModelElement(MPart.class);
		window.getChildren().add(part1);
		window.getChildren().add(part2);
		window.getChildren().add(part3);
		window.setSelectedElement(part2);
		var modelService = applicationContext.get(EModelService.class);
		modelService.move(part1, window, 2);
		assertSame(part1, window.getChildren().get(2));
		assertSame(part2, window.getSelectedElement());
	}

