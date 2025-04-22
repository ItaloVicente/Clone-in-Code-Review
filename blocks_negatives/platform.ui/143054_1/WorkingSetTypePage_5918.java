    /**
     * Called when a working set type is double clicked.
     */
    private void handleDoubleClick() {
        handleSelectionChanged();
        getContainer().showPage(getNextPage());
    }

    /**
     * Called when the selection has changed.
     */
    private void handleSelectionChanged() {
