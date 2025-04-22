    /*
     * Shift the history back or forward
     */
    private void shiftEntry(boolean forward) {
        updateEntry(getEntry(activeEntry));
        if (forward) {
