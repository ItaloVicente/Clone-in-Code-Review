        return null;
    }

    /*
     * Adds the specified entry to the history.
     */
    private void add(NavigationHistoryEntry entry) {
        removeForwardEntries();
        if (history.size() == CAPACITY) {
            NavigationHistoryEntry e = (NavigationHistoryEntry) history
                    .remove(0);
            disposeEntry(e);
        }
        history.add(entry);
        activeEntry = history.size() - 1;
    }

    /*
     * Remove all entries after the active entry.
     */
    private void removeForwardEntries() {
        int length = history.size();
        for (int i = activeEntry + 1; i < length; i++) {
            NavigationHistoryEntry e = (NavigationHistoryEntry) history
                    .remove(activeEntry + 1);
            disposeEntry(e);
        }
    }

    /*
     * Adds a location to the history.
     */
    private void addEntry(IEditorPart part) {
        if (ignoreEntries > 0 || part == null) {
