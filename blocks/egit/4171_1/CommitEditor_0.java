	private void addContributions(IToolBarManager toolBarManager) {
		IMenuService menuService = (IMenuService) getSite().getService(
				IMenuService.class);
		if (menuService != null
				&& toolBarManager instanceof ContributionManager) {
			ContributionManager contributionManager = (ContributionManager) toolBarManager;
			String toolbarUri = "toolbar:" + TOOLBAR_HEADER_ID; //$NON-NLS-1$
			menuService.populateContributionManager(contributionManager,
					toolbarUri);
		}
	}

