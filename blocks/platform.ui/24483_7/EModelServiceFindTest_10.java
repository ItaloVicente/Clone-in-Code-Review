		elements = modelService.findElements(application, clazz,
				EModelService.OUTSIDE_PERSPECTIVE, getSelector(clazz));
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

	public void testFind_MAddons() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		Class<MAddon> clazz = MAddon.class;
		List<MAddon> elements = null;

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(7, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.OUTSIDE_PERSPECTIVE, getSelector(clazz));
		assertEquals(7, elements.size());

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

	public void testFind_MCategory() {
		MApplication application = createApplication();

		EModelService modelService = (EModelService) application.getContext()
				.get(EModelService.class.getName());
		assertNotNull(modelService);

		Class<MCategory> clazz = MCategory.class;
		List<MCategory> elements = null;

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.OUTSIDE_PERSPECTIVE, getSelector(clazz));
		assertEquals(1, elements.size());

