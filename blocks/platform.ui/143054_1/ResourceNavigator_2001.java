				new ActionHandler(group.toggleLinkingAction));
		collapseAllHandler = new CollapseAllHandler(viewer);
		service.activateHandler(CollapseAllHandler.COMMAND_ID, collapseAllHandler);
	}

	private void restoreFilters() {
		IMemento filtersMem = memento.getChild(TAG_FILTERS);

		if (filtersMem != null) { // filters have been defined
			IMemento children[] = filtersMem.getChildren(TAG_FILTER);

			if (children.length > 0 && children[0].getString(TAG_IS_ENABLED) != null) {
