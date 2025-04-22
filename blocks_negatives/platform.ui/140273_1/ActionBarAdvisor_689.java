    /**
     * Disposes the given action.
     * <p>
     * The default implementation checks whether the action is an instance
     * of <code>ActionFactory.IWorkbenchAction</code> and calls its
     * <code>dispose()</code> method if so.
     * Subclasses may extend.
     * </p>
     *
     * @param action the action to dispose
     */
    protected void disposeAction(IAction action) {
        if (action instanceof ActionFactory.IWorkbenchAction) {
            ((ActionFactory.IWorkbenchAction) action).dispose();
        }
    }
