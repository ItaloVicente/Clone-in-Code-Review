		Class<? extends MApplicationElement> clazz = MHandler.class;
		List<? extends MApplicationElement> elements;

		elements = modelService.findElements(application, clazz,
				EModelService.ANYWHERE, getSelector("handler1", clazz, null));
		assertEquals(1, elements.size());
