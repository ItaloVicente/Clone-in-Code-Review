


	public void testMoveWithoutIndexNoOtherElements() {
		MWindow source = BasicFactoryImpl.eINSTANCE.createWindow();

		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		MElementContainer<? extends MUIElement> erase1 = window;
		MElementContainer<MUIElement> target = (MElementContainer<MUIElement>) erase1;

		MPart part = BasicFactoryImpl.eINSTANCE.createPart();
		source.getChildren().add(part);
		MUIElement uiElement = part;
		EModelService modelService = applicationContext.get(EModelService.class);
		modelService.move(uiElement, target);
		assertEquals(part, target.getChildren().get(0));
	}

	public void testMoveWithoutIndexWithOneOtherElements() {
		MWindow source = BasicFactoryImpl.eINSTANCE.createWindow();

		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		MElementContainer<? extends MUIElement> erase1 = window;
		MElementContainer<MUIElement> target = (MElementContainer<MUIElement>) erase1;
		MPart part = BasicFactoryImpl.eINSTANCE.createPart();
		MPart part2 = BasicFactoryImpl.eINSTANCE.createPart();
		source.getChildren().add(part);
		target.getChildren().add(part2);
		EModelService modelService = applicationContext.get(EModelService.class);
		modelService.move(part, target);
		assertSame(part, target.getChildren().get(1));
	}

	public void testMoveWithIndexWithTwoOtherElement() {
		MWindow source = BasicFactoryImpl.eINSTANCE.createWindow();

		MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		MElementContainer<? extends MUIElement> erase1 = window;
		MElementContainer<MUIElement> target = (MElementContainer<MUIElement>) erase1;
		MPart part = BasicFactoryImpl.eINSTANCE.createPart();
		MPart part2 = BasicFactoryImpl.eINSTANCE.createPart();
		MPart part3 = BasicFactoryImpl.eINSTANCE.createPart();
		source.getChildren().add(part);
		target.getChildren().add(part2);
		target.getChildren().add(part3);
		EModelService modelService = applicationContext.get(EModelService.class);
		modelService.move(part, target, 1);
		assertSame(part, target.getChildren().get(1));
	}

