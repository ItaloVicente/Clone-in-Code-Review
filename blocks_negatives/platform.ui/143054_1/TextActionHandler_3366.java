        textSelectAllAction.updateEnabledState();
    }

    /**
     * Set the default <code>IAction</code> handler for the Delete
     * action. This <code>IAction</code> is run only if no active
     * inline text control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Delete action, or <code>null</code> if not interested.
     */
    public void setDeleteAction(IAction action) {
        if (deleteAction == action) {
