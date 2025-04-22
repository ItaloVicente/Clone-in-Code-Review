		List<MToolBarElement> toolBarElements = modelService.findElements(
				application, null, MToolBarElement.class, null,
				EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, toolBarElements.size());

		toolBarElements = modelService.findElements(application, null,
				MToolBarElement.class, null, EModelService.IN_ANY_PERSPECTIVE
						| EModelService.IN_PART);
		assertEquals(2, toolBarElements.size());

		List<MMenuElement> menuElements = modelService.findElements(
				application, null, MMenuElement.class, null,
				EModelService.IN_ANY_PERSPECTIVE);
		assertEquals(0, menuElements.size());

		menuElements = modelService.findElements(application, null,
				MMenuElement.class, null, EModelService.IN_ANY_PERSPECTIVE
						| EModelService.IN_PART);
		assertEquals(3, menuElements.size());

		menuElements = modelService.findElements(application, null,
				MMenuElement.class, null, EModelService.IN_ANY_PERSPECTIVE
						| EModelService.IN_MAIN_MENU);
		assertEquals(2, menuElements.size());
