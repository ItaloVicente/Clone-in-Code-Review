	void disposeHistoryForTab(Object editorTabCookie) {
		PerTabHistory perTabHistory = (PerTabHistory) perTabHistoryMap.remove(editorTabCookie);
		if (perTabHistory != null) {
			if (perTabHistory.currentEntry != null) {
				disposeEntry(perTabHistory.currentEntry);
				perTabHistory.currentEntry = null;
			}
			removeEntriesForTab(perTabHistory.backwardEntries);
			removeEntriesForTab(perTabHistory.forwardEntries);
		}
	}
