		final IMenuService menuService = (IMenuService) window
				.getService(IMenuService.class);
		menuService.populateContributionManager(
				(ContributionManager) customizeActionBars.getMenuManager(),
				MenuUtil.MAIN_MENU);
		ICoolBarManager coolbar = customizeActionBars.getCoolBarManager();
		if (coolbar != null) {
			menuService.populateContributionManager(
					(ContributionManager) coolbar, MenuUtil.MAIN_TOOLBAR);
		}
