	public void testFlags() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		List<MToolBarElement> toolBarElements = modelService.findElements(
				application, null, MToolBarElement.class, null,
				EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, toolBarElements.size());

		toolBarElements = modelService.findElements(application, null,
				MToolBarElement.class, null, EModelService.IN_ANY_PERSPECTIVE
						| EModelService.IN_PART);
		assertEquals(2, toolBarElements.size());

		List<MMenuElement> menuElements = modelService.findElements(
				application, null, MMenuElement.class, null,
				EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, menuElements.size());

		menuElements = modelService.findElements(application, null,
				MMenuElement.class, null, EModelService.IN_ANY_PERSPECTIVE
						| EModelService.IN_PART);
		assertEquals(3, menuElements.size());

		menuElements = modelService.findElements(application, null,
				MMenuElement.class, null, EModelService.IN_ANY_PERSPECTIVE
						| EModelService.IN_MAIN_MENU);
		assertEquals(2, menuElements.size());
	}

	public void testFindHandler() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		MHandler handler1 = CommandsFactoryImpl.eINSTANCE.createHandler();
		handler1.setElementId("handler1");
		application.getHandlers().add(handler1);

		MHandler handler2 = CommandsFactoryImpl.eINSTANCE.createHandler();
		handler2.setElementId("handler2");
		application.getHandlers().add(handler2);

		MHandler foundHandler = null;

		foundHandler = modelService.findHandler(application, "handler1");
		assertNotNull(foundHandler);
		assertSame(handler1, foundHandler);

		foundHandler = modelService.findHandler(application, "invalidId");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(null, "handler1");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(application, "");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(application, null);
		assertNull(foundHandler);
	}

