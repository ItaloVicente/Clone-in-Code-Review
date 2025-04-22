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
    public void registerGlobalAction(IAction action);
