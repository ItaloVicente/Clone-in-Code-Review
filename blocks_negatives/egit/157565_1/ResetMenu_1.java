	/**
	 * @param site
	 * @return a new menu manager representing the "Reset" submenu
	 */
	public static MenuManager createMenu(IWorkbenchSite site) {
		MenuManager resetManager = new MenuManager(
				UIText.GitHistoryPage_ResetMenuLabel, UIIcons.RESET,

		Map<String, String> parameters = new HashMap<>();
		parameters.put(RESET_MODE, ResetType.SOFT.name());
		resetManager.add(getCommandContributionItem(RESET,
				UIText.GitHistoryPage_ResetSoftMenuLabel, parameters, site));

		parameters = new HashMap<>();
		parameters.put(RESET_MODE, ResetType.MIXED.name());
		resetManager.add(getCommandContributionItem(RESET,
				UIText.GitHistoryPage_ResetMixedMenuLabel, parameters, site));

		parameters = new HashMap<>();
		parameters.put(RESET_MODE, ResetType.HARD.name());
		resetManager.add(getCommandContributionItem(RESET,
				UIText.GitHistoryPage_ResetHardMenuLabel, parameters, site));
		return resetManager;
	}

	private static CommandContributionItem getCommandContributionItem(
			String commandId, String menuLabel, Map<String, String> parameters,
			IWorkbenchSite site) {
		CommandContributionItemParameter parameter = new CommandContributionItemParameter(
				site, commandId, commandId, CommandContributionItem.STYLE_PUSH);
		parameter.label = menuLabel;
		parameter.parameters = parameters;
		return new CommandContributionItem(parameter);
	}

