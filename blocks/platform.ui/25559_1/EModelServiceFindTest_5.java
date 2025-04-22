		Class<? extends MApplicationElement> clazz;
		List<? extends MApplicationElement> elements;

		clazz = MToolBarElement.class;
		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE | EModelService.IN_PART,
				getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(2, elements.size());

		clazz = MMenuElement.class;
		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE | EModelService.IN_PART,
				getSelector(clazz));
		assertEquals(4, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ACTIVE_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ACTIVE_PERSPECTIVE | EModelService.IN_PART,
				getSelector(clazz));
		assertEquals(3, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE | EModelService.IN_MAIN_MENU,
				getSelector(clazz));
		assertEquals(9, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_MAIN_MENU, getSelector(clazz));
		assertEquals(9, elements.size());
