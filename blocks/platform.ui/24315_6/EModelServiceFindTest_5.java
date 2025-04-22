		
		List<? extends MApplicationElement> elements;
		
		elements = modelService.findElements(application, null, EModelService.ANYWHERE, getSelector("singleValidId"));
		assertEquals(1, elements.size());
		
		elements = modelService.findElements(application, null, EModelService.ANYWHERE, getSelector("twoValidIds"));
		assertEquals(2, elements.size());
		
		elements = modelService.findElements(application, null, EModelService.ANYWHERE, getSelector("invalidId"));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, null, EModelService.ANYWHERE | EModelService.IN_MAIN_MENU | EModelService.IN_PART, getSelector("menuItem1Id"));
		assertEquals(1, elements.size());
