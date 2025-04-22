						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CHECKOUT,
								UIText.GitHistoryPage_CheckoutMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CREATE_BRANCH,
								UIText.GitHistoryPage_CreateBranchMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CREATE_TAG,
								UIText.GitHistoryPage_CreateTagMenuLabel));
						popupMgr.add(getCommandContributionItem(
								HistoryViewCommands.CREATE_PATCH,
								UIText.GitHistoryPage_CreatePatchMenuLabel));
						popupMgr.add(new Separator());

						MenuManager resetManager = new MenuManager(
								UIText.GitHistoryPage_ResetMenuLabel,
								UIIcons.RESET, "Reset"); //$NON-NLS-1$

						popupMgr.add(resetManager);

						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put(HistoryViewCommands.RESET_MODE, "Soft"); //$NON-NLS-1$
						resetManager.add(getCommandContributionItem(
								HistoryViewCommands.RESET,
								UIText.GitHistoryPage_ResetSoftMenuLabel,
								parameters));
						parameters = new HashMap<String, String>();
						parameters.put(HistoryViewCommands.RESET_MODE, "Mixed"); //$NON-NLS-1$
						resetManager.add(getCommandContributionItem(
								HistoryViewCommands.RESET,
								UIText.GitHistoryPage_ResetMixedMenuLabel,
								parameters));
						parameters = new HashMap<String, String>();
						parameters.put(HistoryViewCommands.RESET_MODE, "Hard"); //$NON-NLS-1$
						resetManager.add(getCommandContributionItem(
								HistoryViewCommands.RESET,
								UIText.GitHistoryPage_ResetHardMenuLabel,
								parameters));
