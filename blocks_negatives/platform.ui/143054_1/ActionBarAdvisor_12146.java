    /**
     * Registers the given action with the key binding service
     * (by calling {@link IActionBarConfigurer#registerGlobalAction(IAction)}),
     * and adds it to the list of actions to be disposed when the window is closed.
     * <p>
     * In order to participate in key bindings, the action must have an action
     * definition id (aka command id), and a corresponding command extension.
     * See the <code>org.eclipse.ui.commands</code> extension point documentation
     * for more details.
     * </p>
     *
     * @param action the action to register, this cannot be <code>null</code>
     *
     * @see IAction#setActionDefinitionId(String)
     * @see #disposeAction(IAction)
     */
    protected void register(IAction action) {
    	Assert.isNotNull(action, "Action must not be null"); //$NON-NLS-1$
        String id = action.getId();
        Assert.isNotNull(id, "Action must not have null id"); //$NON-NLS-1$
