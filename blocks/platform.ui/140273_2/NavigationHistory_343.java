		}
		IMemento editorsMem = memento.createChild(IWorkbenchConstants.TAG_EDITORS);
		for (Iterator iter = editors.iterator(); iter.hasNext();) {
			NavigationHistoryEditorInfo info = (NavigationHistoryEditorInfo) iter.next();
			info.saveState(editorsMem.createChild(IWorkbenchConstants.TAG_EDITOR));
		}

		ArrayList list = new ArrayList(history.size());
		int size = history.size();
		for (int i = 0; i < size; i++) {
			NavigationHistoryEntry entry = (NavigationHistoryEntry) history.get(i);
			if (entry.editorInfo.isPersistable()) {
