    /**
     * Adds actions for "go back", "go home", and "go into" to a menu manager.
     *
     * @param manager is the target manager to update
     */
    public void addNavigationActions(IMenuManager manager) {
        createActions();
        manager.add(homeAction);
        manager.add(backAction);
        manager.add(forwardAction);
        updateNavigationButtons();
    }
