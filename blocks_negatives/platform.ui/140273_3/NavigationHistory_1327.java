    }

    /*
     * Shift the history to the given entry.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    void shiftCurrentEntry(NavigationHistoryEntry entry, boolean forward) {
    	if (isPerTabHistoryEnabled()) {
    		gotoEntryForTab(entry, forward);
    		return;
    	}
        updateEntry(getEntry(activeEntry));
        activeEntry = history.indexOf(entry);
        gotoEntry(entry);
    }

    /**
     * Save the state of this history into the memento.
     */
    void saveState(IMemento memento) {
        NavigationHistoryEntry cEntry = getEntry(activeEntry);
        if (cEntry == null || !cEntry.editorInfo.isPersistable()) {
