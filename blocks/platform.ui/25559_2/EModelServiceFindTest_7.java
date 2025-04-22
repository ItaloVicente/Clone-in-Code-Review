		elements = modelService.findElements(application, clazz,
				EModelService.IN_TRIM, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz,
				EModelService.IN_SHARED_AREA, getSelector(clazz));
		assertEquals(1, elements.size());
