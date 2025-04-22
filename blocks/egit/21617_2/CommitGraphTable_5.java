			popupMgr.add(new Separator());

			MenuManager modifyManager = new MenuManager(
					UIText.GitHistoryPage_ModifyMenuLabel, null, "Modify"); //$NON-NLS-1$

			popupMgr.add(modifyManager);

			if (selectionSize >= 2)
				modifyManager.add(getCommandContributionItem(
						HistoryViewCommands.SQUASH,
						UIText.GitHistoryPage_squashMenuItem));

