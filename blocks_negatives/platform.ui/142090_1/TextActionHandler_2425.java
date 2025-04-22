    /**
     * Set the default <code>IAction</code> handler for the Copy
     * action. This <code>IAction</code> is run only if no active
     * inline text control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Copy action, or <code>null</code> if not interested.
     */
    public void setCopyAction(IAction action) {
        if (copyAction == action) {
