		NavigationHistoryEntry entry = getEntry(activeEntry);
		return entry == null ? null : entry.location;
	}

	public void dispose() {
		disposeHistoryForTabs();
		Iterator e = history.iterator();
		while (e.hasNext()) {
			NavigationHistoryEntry entry = (NavigationHistoryEntry) e.next();
			disposeEntry(entry);
		}
	}

	void setForwardAction(NavigationHistoryAction action) {
		forwardAction = action;
		updateActions();
	}

	void setBackwardAction(NavigationHistoryAction action) {
		backwardAction = action;
		updateActions();
	}

	private NavigationHistoryEntry getEntry(int index) {
		if (0 <= index && index < history.size()) {
