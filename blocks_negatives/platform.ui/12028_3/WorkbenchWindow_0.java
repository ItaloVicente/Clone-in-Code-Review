			IMenuService menuService = (IMenuService) workbench.getService(IMenuService.class);
			menuService.releaseContributions(((ContributionManager) getActionBars()
					.getMenuManager()));
			ICoolBarManager coolbar = getActionBars().getCoolBarManager();
			if (coolbar != null) {
				menuService.releaseContributions(((ContributionManager) coolbar));
			}
