    /**
     * Creates a new contribution item from the given action.
     * The id of the action is used as the id of the item.
     *
     * @param action the action
     */
    public PluginActionCoolBarContributionItem(PluginAction action) {
        super(action);
        setActionSetId(((WWinPluginAction) action).getActionSetId());
    }
