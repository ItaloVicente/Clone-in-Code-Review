    /**
     * Set the default <code>IAction</code> handler for the Cut
     * action. This <code>IAction</code> is run only if no active
     * inline text control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Cut action, or <code>null</code> if not interested.
     */
    public void setCutAction(IAction action) {
        if (cutAction == action) {
