    /**
     * Creates a new action with the given text that monitors selection changes
     * within the given selection provider.
     * The resulting action is added as a listener on the selection provider.
     *
     * @param provider the selection provider that will provide selection notification
     * @param text the string used as the text for the action,
     *   or <code>null</code> if there is no text
     */
    protected SelectionProviderAction(ISelectionProvider provider, String text) {
        super(text);
        this.provider = provider;
        provider.addSelectionChangedListener(this);
    }
