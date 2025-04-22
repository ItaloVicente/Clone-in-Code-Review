    /**
     * Removes a <code>CellEditor</code> from the handler
     * so that the Cut, Copy, Paste, Delete, Select All, Find
     * Undo, and Redo actions are no longer redirected to it.
     *
     * @param editor the <code>CellEditor</code>
     */
    public void removeCellEditor(CellEditor editor) {
        if (editor == null) {
