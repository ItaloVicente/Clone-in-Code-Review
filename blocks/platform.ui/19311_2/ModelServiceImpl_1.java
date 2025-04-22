
		if (searchRoot instanceof MPart && (searchFlags & IN_PART) != 0) {

			if (isTypeSupported(type, MMenuElement.class)) {
				for (MMenu menu : ((MPart) searchRoot).getMenus()) {
					findElementsRecursive(menu, id, type, tagsToMatch, elements, searchFlags);
				}
			}

			MToolBar toolBar = ((MPart) searchRoot).getToolbar();
			if (toolBar != null
					&& (isTypeSupported(type, MToolBar.class) || isTypeSupported(type,
							MToolBarElement.class))) {
				findElementsRecursive(toolBar, id, type, tagsToMatch, elements, searchFlags);
			}
		}
	}

	private boolean isTypeSupported(Class<?> type, Class<?> acceptable) {
		return type == null || acceptable.isAssignableFrom(type);
