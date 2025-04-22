    /**
     * Implement this method to add actions that deal with the currently
     * selected object or objects. Actions should be added to the
     * provided menu object. Current selection can be obtained from
     * the given selection provider.
     *
     * @return <code>true</code> if any contributions were made, and <code>false</code> otherwise.
     */
    boolean contributeObjectActions(IWorkbenchPart part,
            IMenuManager menu, ISelectionProvider selProv,
            List actionIdOverrides);
