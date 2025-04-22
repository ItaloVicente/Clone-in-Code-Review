    /**
     * Creates a new quick menu action with the given command id.
     *
     * @param commandId the command id of the short cut used to open
     *  the sub menu
     */
    public QuickMenuAction(String commandId) {
        setId(commandId);
        setActionDefinitionId(commandId);
    }
