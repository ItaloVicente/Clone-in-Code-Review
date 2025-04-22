        NavigationHistoryEntry entry = getEntry(activeEntry);
        return entry == null ? null : entry.location;
    }

    /**
     * Disposes this NavigationHistory and all entries.
     */
    public void dispose() {
    	disposeHistoryForTabs();
        Iterator e = history.iterator();
        while (e.hasNext()) {
            NavigationHistoryEntry entry = (NavigationHistoryEntry) e.next();
            disposeEntry(entry);
        }
    }

    /**
     * Keeps a reference to the forward action to update its state
     * whenever needed.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    void setForwardAction(NavigationHistoryAction action) {
        forwardAction = action;
        updateActions();
    }

    /**
     * Keeps a reference to the backward action to update its state
     * whenever needed.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    void setBackwardAction(NavigationHistoryAction action) {
        backwardAction = action;
        updateActions();
    }

    /*
     * Returns the history entry indexed by <code>index</code>
     */
    private NavigationHistoryEntry getEntry(int index) {
        if (0 <= index && index < history.size()) {
