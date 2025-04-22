    }

    /**
     * Saves the state of this entry and its location.
     * Returns true if possible otherwise returns false.
     */
    boolean handlePartClosed() {
        if (!editorInfo.isPersistable()) {
