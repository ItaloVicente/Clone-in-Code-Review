    /**
     * Refresh the selection for the action.
     */
    protected void refreshSelection() {
        ISelection selection = window.getSelectionService().getSelection();
        selectionChanged(selection);
    }
