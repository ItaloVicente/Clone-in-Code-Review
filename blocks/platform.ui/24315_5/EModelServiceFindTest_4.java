		menuElements = modelService.findElements(application, null, MMenuElement.class, null, EModelService.IN_ANY_PERSPECTIVE | EModelService.IN_PART);
		assertEquals(4, menuElements.size());
		
		menuElements = modelService.findElements(application, null, MMenuElement.class, null, EModelService.IN_ACTIVE_PERSPECTIVE );
		assertEquals(3, menuElements.size());
		
		menuElements = modelService.findElements(application, null, MMenuElement.class, null, EModelService.IN_ACTIVE_PERSPECTIVE | EModelService.IN_PART);
