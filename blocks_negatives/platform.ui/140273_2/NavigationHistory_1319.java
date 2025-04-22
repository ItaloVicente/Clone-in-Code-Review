        try {
            ignoreEntries++;
            if (entry.editorInfo.memento != null) {
                entry.editorInfo.restoreEditor();
                checkDuplicates(entry.editorInfo);
            }
            entry.restoreLocation();
            updateActions();
        } finally {
            ignoreEntries--;
        }
    }

    /*
     * update the active entry
     */
    private void updateEntry(NavigationHistoryEntry activeEntry) {
        if (activeEntry == null || activeEntry.location == null) {
