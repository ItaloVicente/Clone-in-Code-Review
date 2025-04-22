    /**
     * Sets the global action handler for the action with the given id.
     * <p>
     * Note: Clients who manipulate the global action list are
     * responsible for calling <code>updateActionBars</code> so that the changes
     * can be propagated throughout the workbench.
     * </p>
     *
     * @param actionId an action id declared in the registry
     * @param handler an action which implements the action id, or
     *	<code>null</code> to clear any existing handler
     * @see IWorkbenchActionConstants
     */
    void setGlobalActionHandler(String actionId, IAction handler);
