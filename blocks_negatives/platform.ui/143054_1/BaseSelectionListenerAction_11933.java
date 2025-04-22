    /**
     * Updates this action in response to the given selection.
     * <p>
     * The <code>BaseSelectionListenerAction</code> implementation of this method
     * returns <code>true</code>. Subclasses may extend to react to selection
     * changes; however, if the super method returns <code>false</code>, the
     * overriding method must also return <code>false</code>.
     * </p>
     *
     * @param selection the new selection
     * @return <code>true</code> if the action should be enabled for this selection,
     *   and <code>false</code> otherwise
     */
    protected boolean updateSelection(IStructuredSelection selection) {
        return true;
    }
