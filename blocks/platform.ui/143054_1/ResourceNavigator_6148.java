		if (memento != null) {
			restoreFilters();
			restoreLinkingEnabled();
		}
		frameList = createFrameList();
		initDragAndDrop();
		updateTitle();

		initContextMenu();
