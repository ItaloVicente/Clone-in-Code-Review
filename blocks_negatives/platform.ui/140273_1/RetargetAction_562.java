    /**
     * Constructs a RetargetAction with the given action id and text.
     *
     * @param actionID the retargetable action id
     * @param text the action's text, or <code>null</code> if there is no text
     */
    public RetargetAction(String actionID, String text) {
        this(actionID, text, IAction.AS_UNSPECIFIED);
    }
