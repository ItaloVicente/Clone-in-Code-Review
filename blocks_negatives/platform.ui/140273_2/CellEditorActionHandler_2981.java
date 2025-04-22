        cellCutAction.updateEnabledState();
    }

    /**
     * Sets the default <code>IAction</code> handler for the Delete
     * action. This <code>IAction</code> is run only if no active
     * cell editor control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Delete action, or <code>null</code> if not interested.
     */
    public void setDeleteAction(IAction action) {
        if (deleteAction == action) {
