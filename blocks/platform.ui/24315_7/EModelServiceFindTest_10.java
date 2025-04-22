
	public void testFind_MCommands() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		Class<MCommand> clazz = MCommand.class;
		List<MCommand> elements = null;

		elements = modelService.findElements(application, null, clazz, null,
				EModelService.ANYWHERE);
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, null, clazz, null,
				EModelService.IN_PART);
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, null, clazz, null,
				EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, null, clazz, null,
				EModelService.IN_ACTIVE_PERSPECTIVE);
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, null, clazz, null,
				EModelService.IN_TRIM);
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, null, clazz, null,
				EModelService.IN_SHARED_AREA);
		assertEquals(0, elements.size());
	}

	public void testFind_MBindingContext() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		Class<MBindingContext> clazz = MBindingContext.class;
		List<MBindingContext> elements = null;

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE,
				getSelector("org.eclipse.ui.contexts.window", clazz, null));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(3, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_PART, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ACTIVE_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_TRIM, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_SHARED_AREA, getSelector(clazz));
		assertEquals(0, elements.size());
	}

	public void testFind_MBindingTable() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		Class<MBindingTable> clazz = MBindingTable.class;
		List<MBindingTable> elements = null;

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_PART, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ACTIVE_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_TRIM, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_SHARED_AREA, getSelector(clazz));
		assertEquals(0, elements.size());
	}
