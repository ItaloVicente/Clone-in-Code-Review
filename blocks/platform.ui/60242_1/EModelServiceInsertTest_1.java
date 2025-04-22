
	@Test
	public void testInsertRightOfSharedStack() {
		EModelService modelService = (EModelService) applicationContext.get(EModelService.class.getName());
		assertNotNull(modelService);
		app = modelService.createModelElement(MApplication.class);
		app.setContext(applicationContext);
		MWindow window = modelService.createModelElement(MWindow.class);
		window.setElementId("main.Window");
		app.getChildren().add(window);

		MPartSashContainer psc = modelService.createModelElement(MPartSashContainer.class);
		psc.setHorizontal(true);
		psc.setElementId("topSash");
		window.getChildren().add(psc);

		MPartStack sharedStack = modelService.createModelElement(MPartStack.class);
		sharedStack.setElementId("sharedStack");
		window.getSharedElements().add(sharedStack);

		MPart part1 = BasicFactoryImpl.eINSTANCE.createPart();
		part1.setElementId("part1");
		sharedStack.getChildren().add(part1);

		MPlaceholder sharedStackRef = modelService.createModelElement(MPlaceholder.class);
		sharedStackRef.setElementId(sharedStack.getElementId());
		sharedStackRef.setRef(sharedStack);

		psc.getChildren().add(sharedStackRef);

		MPart newPart = modelService.createModelElement(MPart.class);
		newPart.setElementId("part2");

		modelService.insert(newPart, sharedStack, EModelService.BELOW, 0.5f);
	}
