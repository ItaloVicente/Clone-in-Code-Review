
		if (searchRoot instanceof MPart && (searchFlags & IN_PART) != 0) {
			MPart part = (MPart) searchRoot;

			for (MMenu menu : part.getMenus()) {
				findElementsRecursive(menu, id, type, tagsToMatch, elements, searchFlags);
			}

			MToolBar toolBar = part.getToolbar();
			if (toolBar != null) {
				findElementsRecursive(toolBar, id, type, tagsToMatch, elements, searchFlags);
			}
		}
