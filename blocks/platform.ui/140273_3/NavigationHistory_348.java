			entry.saveState(childMem, list);
			childMem.putInteger(IWorkbenchConstants.TAG_INDEX, editors.indexOf(entry.editorInfo));
		}
	}

	void restoreState(IMemento memento) {
		IMemento editorsMem = memento.getChild(IWorkbenchConstants.TAG_EDITORS);
		IMemento items[] = memento.getChildren(IWorkbenchConstants.TAG_ITEM);
		if (items.length == 0 || editorsMem == null) {
			if (page.getActiveEditor() != null) {
