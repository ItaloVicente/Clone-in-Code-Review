    /**
     * Returns the menu manager.
     * <p>
     * Note: Clients who add or remove items from the returned menu manager are
     * responsible for calling <code>updateActionBars</code> so that the changes
     * can be propagated throughout the workbench.
     * </p>
     *
     * @return the menu manager
     */
    public IMenuManager getMenuManager();
