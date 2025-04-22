	
	public void testFind_MCommands() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext().get(EModelService.class.getName());
		assertNotNull(modelService);
		
		Class<MCommand> clazz = MCommand.class;
		List<MCommand> elements = null;
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.ANYWHERE);
		assertEquals(1, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_PART);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_ACTIVE_PERSPECTIVE);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_TRIM);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_SHARED_AREA);
		assertEquals(0, elements.size());
	}
	
	public void testFind_MBindingContext() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext().get(EModelService.class.getName());
		assertNotNull(modelService);
		
		Class<MBindingContext> clazz = MBindingContext.class;
		List<MBindingContext> elements = null;
		
		elements = modelService.findElements(application, "org.eclipse.ui.contexts.window", clazz, null, EModelService.ANYWHERE);
		assertEquals(1, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.ANYWHERE);
		assertEquals(3, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_PART);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_ACTIVE_PERSPECTIVE);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_TRIM);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_SHARED_AREA);
		assertEquals(0, elements.size());
	}
	
	public void testFind_MBindingTable() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext().get(EModelService.class.getName());
		assertNotNull(modelService);
		
		Class<MBindingTable> clazz = MBindingTable.class;
		List<MBindingTable> elements = null;
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.ANYWHERE);
		assertEquals(1, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_PART);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_ACTIVE_PERSPECTIVE);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_TRIM);
		assertEquals(0, elements.size());
		
		elements = modelService.findElements(application, null, clazz, null, EModelService.IN_SHARED_AREA);
		assertEquals(0, elements.size());
	}
