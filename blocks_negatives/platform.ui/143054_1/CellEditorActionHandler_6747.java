        cellPasteAction.updateEnabledState();
    }

    /**
     * Sets the default <code>IAction</code> handler for the Redo
     * action. This <code>IAction</code> is run only if no active
     * cell editor control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Redo action, or <code>null</code> if not interested.
     */
    public void setRedoAction(IAction action) {
        if (redoAction == action) {
