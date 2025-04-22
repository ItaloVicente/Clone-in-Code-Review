    /**
     * Set the default <code>IAction</code> handler for the Select All
     * action. This <code>IAction</code> is run only if no active
     * inline text control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Select All action, or <code>null</code> if not interested.
     */
    public void setSelectAllAction(IAction action) {
        if (selectAllAction == action) {
