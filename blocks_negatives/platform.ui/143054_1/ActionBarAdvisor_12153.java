    /**
     * Disposes all actions added via <code>register(IAction)</code>
     * using <code>disposeAction(IAction)</code>.
     */
    protected void disposeActions() {
        for (Iterator i = actions.values().iterator(); i.hasNext();) {
            IAction action = (IAction) i.next();
            disposeAction(action);
        }
        actions.clear();
    }
