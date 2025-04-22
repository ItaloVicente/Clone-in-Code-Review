        textCutAction.updateEnabledState();
    }

    /**
     * Set the default <code>IAction</code> handler for the Paste
     * action. This <code>IAction</code> is run only if no active
     * inline text control.
     *
     * @param action the <code>IAction</code> to run for the
     *    Paste action, or <code>null</code> if not interested.
     */
    public void setPasteAction(IAction action) {
        if (pasteAction == action) {
