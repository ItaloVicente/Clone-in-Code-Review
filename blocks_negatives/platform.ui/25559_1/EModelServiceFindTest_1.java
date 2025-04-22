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
	}

	private MHandler findHandler(EModelService ms,
			MApplicationElement searchRoot, final String id) {
		if (searchRoot == null || id == null)
			return null;

		List<MHandler> handlers = ms.findElements(searchRoot, MHandler.class,
				EModelService.ANYWHERE, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return element instanceof MHandler
								&& id.equals(element.getElementId());
					}
				});
		if (handlers.size() > 0) {
			return handlers.get(0);
		}
		return null;
