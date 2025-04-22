
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put(HistoryViewCommands.RESET_MODE, "Soft"); //$NON-NLS-1$
				resetManager.add(getCommandContributionItem(
						HistoryViewCommands.RESET,
						UIText.GitHistoryPage_ResetSoftMenuLabel, parameters));
				parameters = new HashMap<String, String>();
				parameters.put(HistoryViewCommands.RESET_MODE, "Mixed"); //$NON-NLS-1$
				resetManager.add(getCommandContributionItem(
						HistoryViewCommands.RESET,
						UIText.GitHistoryPage_ResetMixedMenuLabel, parameters));
				parameters = new HashMap<String, String>();
				parameters.put(HistoryViewCommands.RESET_MODE, "Hard"); //$NON-NLS-1$
				resetManager.add(getCommandContributionItem(
						HistoryViewCommands.RESET,
						UIText.GitHistoryPage_ResetHardMenuLabel, parameters));
