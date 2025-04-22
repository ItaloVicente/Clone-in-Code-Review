		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_PART, getSelector(clazz));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_ACTIVE_PERSPECTIVE, getSelector(clazz));
