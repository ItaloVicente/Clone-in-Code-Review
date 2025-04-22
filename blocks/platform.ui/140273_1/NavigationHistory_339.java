		try {
			ignoreEntries++;
			if (entry.editorInfo.memento != null) {
				entry.editorInfo.restoreEditor();
				checkDuplicates(entry.editorInfo);
			}
			entry.restoreLocation();
			updateActions();
			printEntries("goto entry"); //$NON-NLS-1$
		} finally {
			ignoreEntries--;
		}
	}

	private void updateEntry(NavigationHistoryEntry activeEntry) {
		if (activeEntry == null || activeEntry.location == null) {
