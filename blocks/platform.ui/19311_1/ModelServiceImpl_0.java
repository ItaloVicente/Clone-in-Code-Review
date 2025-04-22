
		processMenus(searchRoot, id, type, tagsToMatch, elements, searchFlags);
		processToolBars(searchRoot, id, type, tagsToMatch, elements, searchFlags);
	}

	private <T> void processContainer(MUIElement searchRoot, String id, Class<? extends T> type,
			List<String> tagsToMatch, List<T> elements, int searchFlags) {
		MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) searchRoot;
		List<MUIElement> children = container.getChildren();
		boolean excludeToolBars = (searchFlags & IN_TOOLBAR) == 0 && searchRoot instanceof TrimBar;
		for (MUIElement child : children) {
			if (!(excludeToolBars && child instanceof MToolBar)) {
				findElementsRecursive(child, id, type, tagsToMatch, elements, searchFlags);
			}
		}
	}

	private <T> void processMenus(MUIElement element, String id, Class<? extends T> type,
			List<String> tagsToMatch, List<T> elements, int searchFlags) {

		if ((searchFlags & IN_MENU) == 0 || !isTypeSupported(type, MMenuElement.class)) {
			return;
		}

		if (element instanceof MPart) {
			for (MMenu menu : ((MPart) element).getMenus()) {
				findElementsRecursive(menu, id, type, tagsToMatch, elements, searchFlags);
			}
		} else if (element instanceof MWindow) {
			MMenu menu = ((MWindow) element).getMainMenu();
			if (menu != null) {
				findElementsRecursive(menu, id, type, tagsToMatch, elements, searchFlags);
			}
		}
	}

	private <T> void processToolBars(MUIElement element, String id, Class<? extends T> type,
			List<String> tagsToMatch, List<T> elements, int searchFlags) {

		if ((searchFlags & IN_TOOLBAR) == 0
				|| !(element instanceof MPart)
				|| !(isTypeSupported(type, MToolBar.class) || isTypeSupported(type,
						MToolBarElement.class))) {
			return;
		}

		MToolBar toolBar = ((MPart) element).getToolbar();
		if (toolBar != null) {
			findElementsRecursive(toolBar, id, type, tagsToMatch, elements, searchFlags);
		}
	}

	private boolean isTypeSupported(Class<?> type, Class<?> acceptable) {
		return type == null || acceptable.isAssignableFrom(type);
