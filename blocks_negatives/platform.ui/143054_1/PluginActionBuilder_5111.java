    /**
     * The default constructor.
     */
    public PluginActionBuilder() {
    }

    /**
     * Contributes submenus and/or actions into the provided menu and tool bar
     * managers.
     *
     * @param menu the menu to contribute to
     * @param toolbar the toolbar to contribute to
     * @param appendIfMissing append containers if missing
     */
    public final void contribute(IMenuManager menu, IToolBarManager toolbar,
            boolean appendIfMissing) {
        if (cache == null) {
