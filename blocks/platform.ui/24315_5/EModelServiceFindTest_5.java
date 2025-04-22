		List<MHandler> foundHandlers = null;

		foundHandlers = modelService.findElements(application, "handler1", MHandler.class, null);
		assertNotNull(foundHandlers);
		
		foundHandlers = modelService.findElements(application, "handler1", MHandler.class, null, EModelService.IN_PART);
		assertTrue(foundHandlers.isEmpty());

		foundHandlers = modelService.findElements(application, "invalidId", MHandler.class, null);
		assertTrue(foundHandlers.isEmpty());

		foundHandlers = modelService.findElements(application, "", MHandler.class, null);
		assertTrue(foundHandlers.isEmpty());

		foundHandlers = modelService.findElements(application, null, MHandler.class, null);
		assertEquals(8, foundHandlers.size());
		
		foundHandlers = modelService.findElements(application, null, MHandler.class, null, EModelService.ANYWHERE);
		assertEquals(8, foundHandlers.size());
		
		foundHandlers = modelService.findElements(application, null, MHandler.class, null, EModelService.IN_PART);
		assertEquals(4, foundHandlers.size());
		
		foundHandlers = modelService.findElements(application, null, MHandler.class, null, EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(4, foundHandlers.size());
		
		foundHandlers = modelService.findElements(application, null, MHandler.class, null, EModelService.IN_ACTIVE_PERSPECTIVE);
		assertEquals(3, foundHandlers.size());
		
		foundHandlers = modelService.findElements(application, null, MHandler.class, null, EModelService.IN_TRIM);
		assertEquals(0, foundHandlers.size());
		
		foundHandlers = modelService.findElements(application, null, MHandler.class, null, EModelService.IN_SHARED_AREA);
		assertEquals(1, foundHandlers.size());
