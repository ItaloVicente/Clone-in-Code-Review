    /**
     * Returns the global action handler for the action with the given id.
     *
     * @param actionId an action id declared in the registry
     * @return an action handler which implements the action id, or
     *	 <code>null</code> if none is registered
     * @see IWorkbenchActionConstants
     * @see #setGlobalActionHandler(String, IAction)
     */
    public IAction getGlobalActionHandler(String actionId);
