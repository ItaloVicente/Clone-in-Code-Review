        cellSelectAllAction.updateEnabledState();
    }

    /**
     * Sets the default <code>IAction</code> handler for the Undo
     * action. This <code>IAction</code> is run only if no active
     * cell editor control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Undo action, or <code>null</code> if not interested.
     */
    public void setUndoAction(IAction action) {
        if (undoAction == action) {
