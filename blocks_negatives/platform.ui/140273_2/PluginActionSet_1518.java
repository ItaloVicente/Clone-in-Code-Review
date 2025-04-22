    /**
     * PluginActionSet constructor comment.
     *
     * @param desc the descriptor
     */
    public PluginActionSet(ActionSetDescriptor desc) {
        super();
        this.desc = desc;
    }

    /**
     * Adds one plugin action ref to the list.
     *
     * @param action the action
     */
    public void addPluginAction(WWinPluginAction action) {
        pluginActions.add(action);
    }

    /**
     * Returns the list of plugin actions for the set.
     *
     * @return the actions for the set
     */
    public IAction[] getPluginActions() {
        IAction result[] = new IAction[pluginActions.size()];
        pluginActions.toArray(result);
        return result;
    }

    /**
     * Disposes of this action set.
     */
    @Override
