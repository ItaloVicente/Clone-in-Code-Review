		for (Iterator iter = history.iterator(); iter.hasNext();) {
			NavigationHistoryEntry entry = (NavigationHistoryEntry) iter.next();
			if (entry.editorInfo == dup) {
				entry.editorInfo = info;
				info.refCount++;
			}
		}
		editors.remove(dup);
	}


	private static class PerTabHistory {
		LinkedList backwardEntries = new LinkedList();
		NavigationHistoryEntry currentEntry = null;
		LinkedList forwardEntries = new LinkedList();
	}

	private void setNewCurrentEntryForTab(PerTabHistory perTabHistory, NavigationHistoryEntry entry) {
		if (perTabHistory.currentEntry != null) {
			perTabHistory.backwardEntries.addFirst(perTabHistory.currentEntry);
		}
		perTabHistory.currentEntry = entry;
		removeEntriesForTab(perTabHistory.forwardEntries);
	}

	private Object getCookieForTab(IEditorPart part) {
		if (part != null) {
			IWorkbenchPartSite site = part.getSite();
			if (site instanceof PartSite) {
				PartSite partSite = (PartSite) site;
				WorkbenchPartReference ref = (WorkbenchPartReference) partSite.getPartReference();
				if (!ref.isDisposed()) {
