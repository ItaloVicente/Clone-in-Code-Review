    protected ActionFactory(String actionId, String commandId) {
    	this.actionId = actionId;
    	this.commandId = commandId;
    }

    /**
     * Creates a new standard action for the given workbench window. The action
     * has an id as specified by the particular factory.
     * <p>
     * Actions automatically register listeners against the workbench window so
     * that they can keep their enablement state up to date. Ordinarily, the
     * window's references to these listeners will be dropped automatically
     * when the window closes. However, if the client needs to get rid of an
     * action while the window is still open, the client must call
     * {@link IWorkbenchAction#dispose dispose}to give the action an
     * opportunity to deregister its listeners and to perform any other
     * cleanup.
     * </p>
     *
     * @param window
     *            the workbench window
     * @return the workbench action
     */
    public abstract IWorkbenchAction create(IWorkbenchWindow window);

    /**
     * Returns the id of this action factory.
     *
     * @return the id of actions created by this action factory
     */
    public String getId() {
        return actionId;
    }
