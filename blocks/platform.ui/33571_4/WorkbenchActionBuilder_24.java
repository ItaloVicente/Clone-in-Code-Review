	void updatePinActionToolbar() {

		ICoolBarManager coolBarManager = getActionBarConfigurer().getCoolBarManager();
		IContributionItem cbItem = coolBarManager.find(IWorkbenchActionConstants.TOOLBAR_NAVIGATE);
		if (!(cbItem instanceof IToolBarContributionItem)) {
			IDEWorkbenchPlugin.log("Navigation toolbar contribution item is missing"); //$NON-NLS-1$
			return;
		}
		IToolBarContributionItem toolBarItem = (IToolBarContributionItem) cbItem;
		IToolBarManager toolBarManager = toolBarItem.getToolBarManager();
		if (toolBarManager == null) {
			IDEWorkbenchPlugin.log("Navigate toolbar is missing"); //$NON-NLS-1$
			return;
		}

		toolBarManager.update(false);
		toolBarItem.update(ICoolBarManager.SIZE);
	}

	private IContributionItem getPinEditorItem() {
		return ContributionItemFactory.PIN_EDITOR.create(window);
	}

	private IContributionItem getCutItem() {
		return getItem(ActionFactory.CUT.getId(), ActionFactory.CUT.getCommandId(), ISharedImages.IMG_TOOL_CUT,
				ISharedImages.IMG_TOOL_CUT_DISABLED, WorkbenchMessages.Workbench_cut,
