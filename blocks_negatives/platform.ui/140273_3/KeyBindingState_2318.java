    /**
     * Constructs a new instance of <code>KeyBindingState</code> with an
     * empty key sequence, set to reset fully.
     *
     * @param workbenchToNotify
     *            The workbench that this state should keep advised of changes
     *            to the key binding state; must not be <code>null</code>.
     */
    KeyBindingState(IWorkbench workbenchToNotify) {
        currentSequence = KeySequence.getInstance();
        workbench = workbenchToNotify;
        associatedWindow = workbench.getActiveWorkbenchWindow();
    }
