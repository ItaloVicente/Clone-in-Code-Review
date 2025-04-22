	@Test
	public void testMoveWithIndexshouldNotChangeSelectedElement() {
		var source = ems.createModelElement(MWindow.class);
		var window = ems.createModelElement(MWindow.class);
		var part = ems.createModelElement(MPart.class);
		var part2 = ems.createModelElement(MPart.class);
		var part3 = ems.createModelElement(MPart.class);
		source.getChildren().add(part);
		window.getChildren().add(part2);
		window.getChildren().add(part3);
		window.setSelectedElement(part2);
		var modelService = applicationContext.get(EModelService.class);
		modelService.move(part, window, 1);
		assertSame(part, window.getChildren().get(1));
		assertSame(part2, window.getSelectedElement());
	}

