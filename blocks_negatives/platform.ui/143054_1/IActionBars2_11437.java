    /**
     * Returns the cool bar manager.
     * <p>
     * Note: Clients who add or remove items from the returned cool bar manager are
     * responsible for calling <code>updateActionBars</code> so that the changes
     * can be propagated throughout the workbench.
     * </p>
     *
     * @return the cool bar manager.
     */
    public ICoolBarManager getCoolBarManager();
