    /**
     * Adds actions for "go back", "go home", and "go into" to a tool bar manager.
     *
     * @param toolBar is the target manager to update
     */
    public void addNavigationActions(IToolBarManager toolBar) {
        createActions();
        toolBar.add(homeAction);
        toolBar.add(backAction);
        toolBar.add(forwardAction);
        updateNavigationButtons();
    }
