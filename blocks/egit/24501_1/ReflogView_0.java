
		MenuManager resetManager = new MenuManager(
				UIText.GitHistoryPage_ResetMenuLabel, UIIcons.RESET, "Reset"); //$NON-NLS-1$
		menuManager.add(resetManager);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(HistoryViewCommands.RESET_MODE, ResetType.SOFT.name());
		resetManager.add(getCommandContributionItem(HistoryViewCommands.RESET,
				UIText.GitHistoryPage_ResetSoftMenuLabel, parameters));
		parameters = new HashMap<String, String>();
		parameters.put(HistoryViewCommands.RESET_MODE, ResetType.MIXED.name());
		resetManager.add(getCommandContributionItem(HistoryViewCommands.RESET,
				UIText.GitHistoryPage_ResetMixedMenuLabel, parameters));
		parameters = new HashMap<String, String>();
		parameters.put(HistoryViewCommands.RESET_MODE, ResetType.HARD.name());
		resetManager.add(getCommandContributionItem(HistoryViewCommands.RESET,
				UIText.GitHistoryPage_ResetHardMenuLabel, parameters));

