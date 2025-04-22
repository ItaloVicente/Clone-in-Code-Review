    /**
     * Returns the tool bar manager.
     * <p>
     * Note: Clients who add or remove items from the returned tool bar manager are
     * responsible for calling <code>updateActionBars</code> so that the changes
     * can be propagated throughout the workbench.
     * </p>
     *
     * @return the tool bar manager
     */
    public IToolBarManager getToolBarManager();
