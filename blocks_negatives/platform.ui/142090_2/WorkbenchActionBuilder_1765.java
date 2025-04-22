     * Update the pin action's tool bar
     */
    void updatePinActionToolbar() {

        ICoolBarManager coolBarManager = getActionBarConfigurer()
                .getCoolBarManager();
        IContributionItem cbItem = coolBarManager
                .find(IWorkbenchActionConstants.TOOLBAR_NAVIGATE);
        if (!(cbItem instanceof IToolBarContributionItem)) {
            IDEWorkbenchPlugin
            return;
        }
        IToolBarContributionItem toolBarItem = (IToolBarContributionItem) cbItem;
        IToolBarManager toolBarManager = toolBarItem.getToolBarManager();
        if (toolBarManager == null) {
            return;
        }
