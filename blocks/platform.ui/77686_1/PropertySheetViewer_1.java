
	void dispose() {
		if (tree != null && !tree.isDisposed()) {
			tree.dispose();
		}
		activationListeners.clear();
		entryToItemMap.clear();
		cellEditor = null;
		editorListener = null;
		entryListener = null;
		input = null;
		rootEntry = null;
		sorter = null;
		statusLineManager = null;
		tree = null;
		treeEditor = null;
	}
