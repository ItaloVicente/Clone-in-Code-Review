		List<? extends MApplicationElement> elements;
		
		elements = modelService.findElements(application, null, EModelService.IN_PART, getSelector("singleValidId"));
		assertEquals(0, elements.size());

		elements = modelService.findElements(application, MPartSashContainer.class, EModelService.ANYWHERE, getSelector("twoValidIds", MPartSashContainer.class, tags));
		assertEquals(1, elements.size());
