        cellDeleteAction.updateEnabledState();
    }

    /**
     * Sets the default <code>IAction</code> handler for the Find
     * action. This <code>IAction</code> is run only if no active
     * cell editor control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Find action, or <code>null</code> if not interested.
     */
    public void setFindAction(IAction action) {
        if (findAction == action) {
