	void updatePinActionToolbar() {

		ICoolBarManager coolBarManager = getActionBarConfigurer()
				.getCoolBarManager();
		IContributionItem cbItem = coolBarManager
				.find(IWorkbenchActionConstants.TOOLBAR_NAVIGATE);
		if (!(cbItem instanceof IToolBarContributionItem)) {
			IDEWorkbenchPlugin
					.log("Navigation toolbar contribution item is missing"); //$NON-NLS-1$
			return;
		}
		IToolBarContributionItem toolBarItem = (IToolBarContributionItem) cbItem;
		IToolBarManager toolBarManager = toolBarItem.getToolBarManager();
		if (toolBarManager == null) {
			IDEWorkbenchPlugin.log("Navigate toolbar is missing"); //$NON-NLS-1$
			return;
		}
