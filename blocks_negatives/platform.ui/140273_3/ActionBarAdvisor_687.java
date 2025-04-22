    /**
     * Returns the action with the given id, or <code>null</code> if not found.
     *
     * @param id the action id
     * @return the action with the given id, or <code>null</code> if not found
     * @see IAction#getId()
     */
    protected IAction getAction(String id) {
        return (IAction) actions.get(id);
    }
