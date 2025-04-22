	void saveState(IMemento memento) {
		NavigationHistoryEntry cEntry = getEntry(activeEntry);
		if (cEntry == null || !cEntry.editorInfo.isPersistable()) {
			return;
		}

		ArrayList editors = (ArrayList) this.editors.clone();
		for (Iterator iter = editors.iterator(); iter.hasNext();) {
			NavigationHistoryEditorInfo info = (NavigationHistoryEditorInfo) iter.next();
			if (!info.isPersistable()) {
