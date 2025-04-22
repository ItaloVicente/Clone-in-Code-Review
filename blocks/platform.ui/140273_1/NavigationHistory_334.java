		addEntry(part);
	}

	NavigationHistoryEntry[] getBackwardEntries() {
		if (isPerTabHistoryEnabled()) {
			return getEntriesForTab(false);
		}
		int length = activeEntry;
		NavigationHistoryEntry[] entries = new NavigationHistoryEntry[length];
		for (int i = 0; i < activeEntry; i++) {
			entries[activeEntry - 1 - i] = getEntry(i);
		}
		return entries;
	}

	NavigationHistoryEntry[] getForwardEntries() {
		if (isPerTabHistoryEnabled()) {
			return getEntriesForTab(true);
		}
		int length = history.size() - activeEntry - 1;
		length = Math.max(0, length);
		NavigationHistoryEntry[] entries = new NavigationHistoryEntry[length];
		for (int i = activeEntry + 1; i < history.size(); i++) {
			entries[i - activeEntry - 1] = getEntry(i);
		}
		return entries;
	}

	@Override
