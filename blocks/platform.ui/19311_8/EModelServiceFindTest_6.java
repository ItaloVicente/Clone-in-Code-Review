		List<MMenuElement> menuElements = modelService.findElements(
				application, null, MMenuElement.class, null);
		assertEquals(5, menuElements.size());

		List<MToolBarElement> toolBarElements = modelService.findElements(
				application, null, MToolBarElement.class, null);
		assertEquals(2, toolBarElements.size());

