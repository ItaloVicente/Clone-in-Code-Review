		elements = modelService.findElements(application, MPart.class,
				EModelService.ANYWHERE, getSelector(MPart.class));
		assertEquals(5, elements.size());

		elements = modelService.findElements(application, MPartStack.class,
				EModelService.ANYWHERE, getSelector(MPartStack.class));
		assertEquals(3, elements.size());
