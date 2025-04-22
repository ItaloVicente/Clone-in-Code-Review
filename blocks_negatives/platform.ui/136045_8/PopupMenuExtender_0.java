		registerE4Support();
		cleanUpContributionCache();
	}

	private void registerE4Support() {
		if (menuModel.getWidget() == null && menu.getMenu() != null) {
			MenuService.registerMenu(menu.getMenu().getParent(), menuModel, context);
		}
