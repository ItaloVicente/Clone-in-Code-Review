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

        toolBarManager.update(false);
        toolBarItem.update(ICoolBarManager.SIZE);
    }
    
    private IContributionItem getPinEditorItem() {
    	return ContributionItemFactory.PIN_EDITOR.create(window);
    }
    
    private IContributionItem getCutItem() {
		return getItem(
				ActionFactory.CUT.getId(),
				ActionFactory.CUT.getCommandId(),
				ISharedImages.IMG_TOOL_CUT,
				ISharedImages.IMG_TOOL_CUT_DISABLED,
				WorkbenchMessages.Workbench_cut,
