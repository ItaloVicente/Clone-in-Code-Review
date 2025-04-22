        editorInfo = null;
    }

    /**
     * Merges this entry into the current entry. Returns true
     * if the merge was possible otherwise returns false.
     */
    boolean mergeInto(NavigationHistoryEntry currentEntry) {
        if (editorInfo.editorInput != null
                && editorInfo.editorInput
                        .equals(currentEntry.editorInfo.editorInput)) {
            if (location != null) {
                if (currentEntry.location == null) {
                    currentEntry.location = location;
                    location = null;
                    return true;
                }
