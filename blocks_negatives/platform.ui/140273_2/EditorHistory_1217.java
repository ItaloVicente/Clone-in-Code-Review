    /**
     * Adds an item to the history.
     */
    private void add(EditorHistoryItem newItem, int index) {
        if (newItem.isRestored()) {
            remove(newItem.getInput());
        }
