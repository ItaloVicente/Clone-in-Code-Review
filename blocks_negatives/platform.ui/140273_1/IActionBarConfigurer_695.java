    /**
     * Returns the cool bar manager of the workbench window.
     *
     * @return the cool bar manager
     */
    ICoolBarManager getCoolBarManager();

    /**
     * Register the action as a global action with a workbench
     * window.
     * <p>
     * For a workbench retarget action
     * ({@link org.eclipse.ui.actions.RetargetAction RetargetAction})
     * to work, it must be registered.
     * You should also register actions that will participate
     * in custom key bindings.
     * </p>
     *
     * @param action the global action
     * @see org.eclipse.ui.actions.RetargetAction
     */
    void registerGlobalAction(IAction action);
