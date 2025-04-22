    /**
     * Notifies this action that the given structured selection has changed.
     * <p>
     * The <code>BaseSelectionListenerAction</code> implementation of this method
     * records the given selection for future reference and calls
     * <code>updateSelection</code>, updating the enable state of this action
     * based on the outcome. Subclasses should override <code>updateSelection</code>
     * to react to selection changes.
     * </p>
     *
     * @param selection the new selection
     */
    public final void selectionChanged(IStructuredSelection selection) {
        if (running) {
            deferredSelection = selection;
            return;
        }
        this.selection = selection;
        clearCache();
        setEnabled(updateSelection(selection));
    }
