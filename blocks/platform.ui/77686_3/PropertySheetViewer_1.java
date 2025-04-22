
	void dispose() {
		if (tree != null && !tree.isDisposed()) {
			tree.dispose();
		}
		if (rootEntry != null) {
			if (entryListener != null) {
				rootEntry.removePropertySheetEntryListener(entryListener);
			}
			rootEntry = null;
		}
		activationListeners.clear();
		entryToItemMap.clear();
		cellEditor = null;
		editorListener = null;
		entryListener = null;
		input = null;
		sorter = null;
		statusLineManager = null;
		tree = null;
		treeEditor = null;
	}
