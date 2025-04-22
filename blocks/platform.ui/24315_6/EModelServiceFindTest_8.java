		elements = modelService.findElements(application, clazz, EModelService.ANYWHERE, getSelector("handler1", clazz, null));
		assertEquals(1, elements.size());
		
		elements = modelService.findElements(application, clazz, EModelService.IN_PART, getSelector("handler1", clazz, null));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz, EModelService.ANYWHERE, getSelector("invalidId", clazz, null));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, null, MHandler.class, null);
		assertEquals(8, elements.size());
		
		elements = modelService.findElements(application, clazz, EModelService.ANYWHERE, getSelector(clazz));
		assertEquals(8, elements.size());

		elements = modelService.findElements(application, clazz, EModelService.IN_PART, getSelector(clazz));
		assertEquals(4, elements.size());
		
		elements = modelService.findElements(application, clazz, EModelService.IN_ANY_PERSPECTIVE, getSelector(clazz));
		assertEquals(4, elements.size());
		
		elements = modelService.findElements(application, clazz, EModelService.IN_ACTIVE_PERSPECTIVE, getSelector(clazz));
		assertEquals(3, elements.size());
		
		elements = modelService.findElements(application, clazz, EModelService.IN_TRIM, getSelector(clazz));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, clazz, EModelService.IN_SHARED_AREA, getSelector(clazz));
		assertEquals(1, elements.size());
