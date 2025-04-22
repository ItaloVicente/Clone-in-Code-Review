			final IMenuService menuService = (IMenuService) serviceLocator
					.getService(IMenuService.class);
			menuService.populateContributionManager((ContributionManager) getActionBars()
					.getMenuManager(), MenuUtil.MAIN_MENU);
			ICoolBarManager coolbar = getActionBars().getCoolBarManager();
			if (coolbar != null) {
				menuService.populateContributionManager((ContributionManager) coolbar,
						MenuUtil.MAIN_TOOLBAR);
			}
