		clazz = MToolBarElement.class;
		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE, getSelector(clazz));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ANY_PERSPECTIVE | EModelService.IN_PART,
				getSelector(clazz));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_PART, getSelector(clazz));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ACTIVE_PERSPECTIVE, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(3, elements.size());

